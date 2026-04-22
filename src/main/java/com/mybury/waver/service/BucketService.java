package com.mybury.waver.service;

import com.mybury.waver.common.code.FixedKeyword;
import com.mybury.waver.common.code.ReportType;
import com.mybury.waver.common.code.ResultCode;
import com.mybury.waver.common.code.SortType;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.repository.UserRepository;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.User;
import com.mybury.waver.domain.vo.BucketGoalCount;
import com.mybury.waver.event.message.BadgeCountEvent;
import com.mybury.waver.exception.WaverException;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.LikeBucketRepository;
import com.mybury.waver.repository.ReportRepository;
import com.mybury.waver.util.FileUploadUtils;
import com.mybury.waver.web.message.v1.bucket.BucketCreateRequest;
import com.mybury.waver.web.message.v1.bucket.BucketDetailResponse;
import com.mybury.waver.web.message.v1.bucket.BucketRequest;
import com.mybury.waver.web.message.v1.bucket.BucketUpdateRequest;
import com.mybury.waver.web.message.v1.bucket.GetPopularBucketResponse;
import com.mybury.waver.web.message.v1.bucket.KeywordElement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class BucketService {

  private final FileUploadUtils fileUploadUtils;
  private final BucketRepository bucketRepository;
  private final LikeBucketRepository likeBucketRepository;
  private final ReportRepository reportRepository;
  private final UserRepository userRepository;
  private final ApplicationEventPublisher publisher;

  @Transactional
  public BucketDetailResponse create(long userId, BucketCreateRequest request) {
    Bucket bucket = request.toBucket(userId);

    if (!ObjectUtils.isEmpty(request.images())) {
      String imageUrl = request.images().stream().map(fileUploadUtils::uploadFile)
          .collect(Collectors.joining(","));
      bucket.setImgUrl(imageUrl);
    }
    Bucket saved = bucketRepository.save(bucket);
    bucketRepository.commit();

    processBadgeCount(userId, request.keywords());

    return bucketDetail(saved.getId(), userId);
  }

  private void processBadgeCount(long userId, String joinedKeywords) {
    if (!StringUtils.hasText(joinedKeywords)) {
      return;
    }
    List<String> keywords = Arrays.stream(joinedKeywords.split(","))
        .map(String::trim)
        .filter(StringUtils::hasText).toList();

    publisher.publishEvent(new BadgeCountEvent(userId, keywords));
  }

  @Transactional
  public BucketDetailResponse update(long id, long userId, BucketUpdateRequest request) {
    Bucket bucket = request.toBucket(userId);
    bucket.setId(id);

    if (!ObjectUtils.isEmpty(request.images())) {
      String imageUrl = request.images().stream().map(fileUploadUtils::uploadFile)
          .collect(Collectors.joining(","));
      bucket.setImgUrl(imageUrl);
    }
    bucketRepository.save(bucket);
    bucketRepository.commit();
    return bucketDetail(id, userId);
  }

  public List<Bucket> bucketList(long userId, @Valid BucketRequest request) {
    Long targetUserId = null;
    boolean hasMyBucket = request.hasMyBucket() == YesNo.Y;
    if (hasMyBucket) {
      targetUserId = userId;
    }

    List<Long> reportedBucketIds =
        reportRepository.findBucketlistIdsByReportUserIdAndReportType(userId, ReportType.BUCKET);
    if (reportedBucketIds == null || reportedBucketIds.isEmpty()) {
      return bucketRepository.findBucket(targetUserId, request);
    }

    return bucketRepository.findBucketExcludingIds(targetUserId, request, reportedBucketIds);
  }

  public GetPopularBucketResponse popularBucket(Long userId) {
    //  bucketRepository.findBucket 를 두번 요청
    // popularElements 의 경우 추천순으로 최근 한달 것 조회
    // recommendElements 의 경우 최신순으로 최근 한달 것 조회
    LocalDate now = LocalDate.now();
    LocalDate from = now.minusMonths(1);

    BucketRequest popularRequest = new BucketRequest(
        null,
        null,
        null,
        SortType.LIKE_COUNT_DESC,
        null,
        null,
        YesNo.N,
        from,
        now,
        4,
        null
    );

    BucketRequest recommendRequest = new BucketRequest(
        null,
        null,
        null,
        SortType.CREATED_DESC,
        null,
        null,
        YesNo.N,
        from,
        now,
        4,
        YesNo.Y
    );

    List<Long> reportedBucketIds = null;
    if (userId != null) {
      reportedBucketIds = reportRepository.findBucketlistIdsByReportUserIdAndReportType(userId, ReportType.BUCKET);
    }

    List<Bucket> popularBucketList;
    List<Bucket> recommendBucketList;

    if (reportedBucketIds == null || reportedBucketIds.isEmpty()) {
      popularBucketList = bucketRepository.findBucket(null, popularRequest);
      recommendBucketList = bucketRepository.findBucket(null, recommendRequest);
    } else {
      popularBucketList = bucketRepository.findBucketExcludingIds(null, popularRequest, reportedBucketIds);
      recommendBucketList = bucketRepository.findBucketExcludingIds(null, recommendRequest, reportedBucketIds);
    }

    return GetPopularBucketResponse.of(popularBucketList, recommendBucketList);
  }

  public BucketDetailResponse bucketDetail(long id, long userId) {
    Bucket bucket = bucketRepository.findByIdAndDeleted(id, YesNo.N);
    List<KeywordElement> keywords = new ArrayList<>();
    if (StringUtils.hasText(bucket.getKeywords())) {
      String[] selectedKeyword = bucket.getKeywords().split(",");
      Arrays.stream(selectedKeyword)
          .forEach(item -> keywords.add(new KeywordElement(FixedKeyword.get(item))));
    }
    boolean isLike = likeBucketRepository.existsByUserIdAndBucketId(userId, id);

    List<User> friendUserList = new ArrayList<>();
    if (StringUtils.hasText(bucket.getFriendUserIds())) {
      List<Long> friendIds = Arrays.stream(bucket.getFriendUserIds().split(","))
          .map(String::trim)
          .filter(StringUtils::hasText)
          .map(Long::parseLong)
          .toList();
      friendUserList = userRepository.findAllById(friendIds);
    }

    return BucketDetailResponse.of(bucket, userId, keywords, friendUserList, isLike);
  }

  public void delete(long id, long userId) {
    bucketRepository.deleteBucket(id, userId);
  }

  public void achieve(long id, long userId) {
    BucketGoalCount goalCount = bucketRepository.findByIdAndUserId(id, userId)
        .orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));

    if (goalCount.getGoalCount() == goalCount.getUserCount() + 1) {
      bucketRepository.complete(id, userId);
    } else {
      bucketRepository.achieve(id, userId);
    }
  }

  public void achieveCancel(long id, long userId) {
    bucketRepository.achieveCancel(id, userId);
  }

  public void reset(long id, long userId) {
    bucketRepository.reset(id, userId);
  }

  public void patchGoalCount(long id, long userId, int goalCount) {
    bucketRepository.updateGoalCount(id, userId, goalCount);
  }
}
