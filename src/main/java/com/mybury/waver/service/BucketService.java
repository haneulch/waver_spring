package com.mybury.waver.service;

import com.mybury.waver.common.code.*;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.FreeTier;
import com.mybury.waver.domain.User;
import com.mybury.waver.event.message.BadgeCountEvent;
import com.mybury.waver.exception.WaverException;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.FreeTierRepository;
import com.mybury.waver.repository.LikeBucketRepository;
import com.mybury.waver.repository.ReportRepository;
import com.mybury.waver.repository.UserRepository;
import com.mybury.waver.util.FileUploadUtils;
import com.mybury.waver.web.message.v1.bucket.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BucketService {

  private static final int POPULAR_BUCKET_LIMIT = 4;
  private static final int MAX_IMAGE_COUNT = 3;

  private final FileUploadUtils fileUploadUtils;
  private final BucketRepository bucketRepository;
  private final LikeBucketRepository likeBucketRepository;
  private final ReportRepository reportRepository;
  private final UserRepository userRepository;
  private final FreeTierRepository freeTierRepository;
  private final ApplicationEventPublisher publisher;

  @Transactional
  public BucketDetailResponse create(long userId, BucketCreateRequest request) {
    int imageCount = ObjectUtils.isEmpty(request.images()) ? 0 : request.images().size();
    checkSaveLimits(userId, imageCount,
        imageCount > 1,
        StringUtils.hasText(request.friendUserIds()));

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

  /**
   * 저장 제한 검사 및 무료 사용 횟수 차감.
   * <p>
   * - 이미지: 구독 여부와 무관하게 최대 {@value MAX_IMAGE_COUNT}개
   * - 무료 사용자: 이미지 2개 이상 저장은 제공된 횟수(기본 1회)만, 함께하기는 제공된 횟수(기본 3회)만 가능
   * - 구독(ACTIVE) 사용자: 이미지 하드캡 외 제한 없음
   */
  private void checkSaveLimits(long userId, int imageCount, boolean usesMultiImage, boolean usesTogether) {
    if (imageCount > MAX_IMAGE_COUNT) {
      throw new WaverException(ResultCode.IMAGE_LIMIT_EXCEEDED);
    }

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));
    if (user.getPremiumStatus() == PremiumStatus.ACTIVE) {
      return;
    }

    if (!usesMultiImage && !usesTogether) {
      return;
    }

    FreeTier freeTier = freeTierRepository.findByUserId(userId)
        .orElseGet(() -> freeTierRepository.save(FreeTier.createDefaultFreeTier(userId)));

    if (usesMultiImage) {
      if (!freeTier.canUseMultiImage()) {
        throw new WaverException(ResultCode.IMAGE_LIMIT_EXCEEDED);
      }
      freeTier.useMultiImage();
    }

    if (usesTogether) {
      if (!freeTier.canUseTogether()) {
        throw new WaverException(ResultCode.TOGETHER_LIMIT_EXCEEDED);
      }
      freeTier.useTogether();
    }
  }

  private int countImages(String imgUrl) {
    if (!StringUtils.hasText(imgUrl)) {
      return 0;
    }
    return imgUrl.split(",").length;
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
    Bucket bucket = bucketRepository.findByIdAndDeleted(id, YesNo.N)
        .orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));
    if (!bucket.getUserId().equals(userId)) {
      throw new WaverException(ResultCode.FORBIDDEN);
    }

    // 무료 사용 횟수는 새로 추가되는 경우에만 차감한다 (기존 상태 유지 수정은 무료)
    int imageCount = ObjectUtils.isEmpty(request.images()) ? 0 : request.images().size();
    boolean addsMultiImage = imageCount > 1 && countImages(bucket.getImgUrl()) <= 1;
    boolean addsTogether = StringUtils.hasText(request.friendUserIds())
        && !StringUtils.hasText(bucket.getFriendUserIds());
    checkSaveLimits(userId, imageCount, addsMultiImage, addsTogether);

    // 관리 중인 엔티티에 요청 필드만 반영한다.
    // (새 객체를 merge 하면 요청에 없는 type/likeCount/status 등이 빌더 기본값으로 덮여 초기화된다)
    bucket.setTitle(request.title());
    bucket.setMemo(request.memo());
    bucket.setCategoryId(request.categoryId());
    bucket.setExposureStatus(request.exposureStatus());
    bucket.setTargetDate(request.targetDate());
    bucket.setGoalCount(request.goalCount());
    bucket.setKeywords(request.keywords());
    bucket.setFriendUserIds(request.friendUserIds());
    if (request.bucketType() != null) {
      bucket.setType(request.bucketType());
    }
    if (request.scrapYn() != null) {
      bucket.setScrapYn(request.scrapYn());
    }
    if (!ObjectUtils.isEmpty(request.images())) {
      String imageUrl = request.images().stream().map(fileUploadUtils::uploadFile)
          .collect(Collectors.joining(","));
      bucket.setImgUrl(imageUrl);
    }
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

    return findBuckets(targetUserId, request, reportedBucketIds);
  }

  private List<Bucket> findBuckets(Long targetUserId, BucketRequest request, List<Long> reportedBucketIds) {
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
        POPULAR_BUCKET_LIMIT,
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
        POPULAR_BUCKET_LIMIT,
        YesNo.Y
    );

    List<Long> reportedBucketIds = null;
    if (userId != null) {
      reportedBucketIds = reportRepository.findBucketlistIdsByReportUserIdAndReportType(userId, ReportType.BUCKET);
    }

    List<Bucket> popularBucketList = findBuckets(null, popularRequest, reportedBucketIds);
    List<Bucket> recommendBucketList = findBuckets(null, recommendRequest, reportedBucketIds);

    return GetPopularBucketResponse.of(popularBucketList, recommendBucketList);
  }

  public BucketDetailResponse bucketDetail(long id, long userId) {
    Bucket bucket = bucketRepository.findByIdAndDeleted(id, YesNo.N)
        .orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));
    
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
    Bucket bucket = getBucketForCount(id, userId);

    if (bucket.getGoalCount() == bucket.getUserCount() + 1) {
      bucketRepository.complete(id, bucket.getUserId());
    } else {
      bucketRepository.achieve(id, bucket.getUserId());
    }
  }

  public void achieveCancel(long id, long userId) {
    Bucket bucket = getBucketForCount(id, userId);
    bucketRepository.achieveCancel(id, bucket.getUserId());
  }

  // 달성 횟수는 소유자 또는 함께하기(TOGETHER) 친구만 변경할 수 있다
  private Bucket getBucketForCount(long id, long userId) {
    Bucket bucket = bucketRepository.findByIdAndDeleted(id, YesNo.N)
        .orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));

    boolean isOwner = bucket.getUserId() != null && bucket.getUserId() == userId;
    if (!isOwner && !isTogetherFriend(bucket, userId)) {
      throw new WaverException(ResultCode.FORBIDDEN);
    }
    return bucket;
  }

  private boolean isTogetherFriend(Bucket bucket, long userId) {
    if (bucket.getType() != ContentType.TOGETHER || !StringUtils.hasText(bucket.getFriendUserIds())) {
      return false;
    }
    String target = String.valueOf(userId);
    return Arrays.stream(bucket.getFriendUserIds().split(","))
        .map(String::trim)
        .anyMatch(target::equals);
  }

  public void reset(long id, long userId) {
    bucketRepository.reset(id, userId);
  }

  public void patchGoalCount(long id, long userId, int goalCount) {
    bucketRepository.updateGoalCount(id, userId, goalCount);
  }
}
