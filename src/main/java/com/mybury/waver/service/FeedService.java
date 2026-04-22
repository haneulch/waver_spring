package com.mybury.waver.service;

import com.mybury.waver.common.code.ReportType;
import com.mybury.waver.common.code.ResultCode;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.LikeBucket;
import com.mybury.waver.domain.Report;
import com.mybury.waver.domain.UserKeyword;
import com.mybury.waver.event.message.BucketReportedEvent;
import com.mybury.waver.exception.WaverException;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.CategoryRepository;
import com.mybury.waver.repository.LikeBucketRepository;
import com.mybury.waver.repository.ReportRepository;
import com.mybury.waver.repository.UserKeywordRepository;
import com.mybury.waver.web.message.v1.feed.FeedResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {

  private final BucketRepository bucketRepository;
  private final ReportRepository reportRepository;
  private final UserKeywordRepository userKeywordRepository;
  private final LikeBucketRepository likeBucketRepository;
  private final CategoryRepository categoryRepository;
  private final ApplicationEventPublisher publisher;

  @Transactional
  public void report(long id, String reason, long userId) {
    Bucket bucket = bucketRepository.findByIdAndDeleted(id, YesNo.N);
    if (bucket == null) {
      throw new WaverException(ResultCode.NOT_FOUND);
    }
    if (bucket.getUserId() != null && bucket.getUserId().equals(userId)) {
      throw new WaverException(ResultCode.FORBIDDEN);
    }

    boolean isDuplicated = reportRepository.existsByReportUserIdAndBucketlistIdAndReportType(userId, id,
      ReportType.BUCKET);
    if (isDuplicated) {
      return;
    }

    Report report = Report.builder()
      .reportType(ReportType.BUCKET)
      .bucketlistId(id)
      .reason(reason)
      .reportUserId(userId)
      .build();
    reportRepository.save(report);

    publisher.publishEvent(new BucketReportedEvent(id));
  }

  public List<FeedResponse.FeedElement> feeds(Long userId, Long nextKey) {

    List<UserKeyword> keywords = userKeywordRepository.findByUserId(userId);
    if (CollectionUtils.isEmpty(keywords)) {
      throw new WaverException(ResultCode.KEYWORD_NOT_FOUND);
    }

    // 관심 키워드
    List<String> keywordCodes = keywords.stream()
      .map(UserKeyword::getCode)
      .toList();

    List<Long> reportedBucketIds =
        reportRepository.findBucketlistIdsByReportUserIdAndReportType(userId, ReportType.BUCKET);

    List<Bucket> buckets = bucketRepository.findFeed(keywordCodes, userId, nextKey, reportedBucketIds);

    // 사용자가 좋아요한 버킷 ID들을 한 번에 조회
    Set<Long> likedBucketIds = buckets.stream()
      .map(Bucket::getId)
      .collect(Collectors.toSet());

    Set<Long> userLikedBucketIds = likeBucketRepository.findByUserIdAndBucketIdIn(userId, likedBucketIds)
      .stream()
      .map(LikeBucket::getBucketId)
      .collect(Collectors.toSet());

    return buckets.stream()
      .map(bucket -> {
        YesNo likeYn = userLikedBucketIds.contains(bucket.getId()) ? YesNo.Y : YesNo.N;
        return FeedResponse.FeedElement.of(bucket, likeYn);
      })
      .toList();
  }

  @Transactional
  public void keyword(long userId, List<String> keywordCodes) {
    List<UserKeyword> keywords = keywordCodes.stream()
      .map(code -> UserKeyword.builder().userId(userId).code(code).build()).toList();
    userKeywordRepository.saveAll(keywords);
  }

  public long copy(long userId, long id) {
    Bucket bucket = bucketRepository.findByIdAndDeletedAndScrapYn(id, YesNo.N, YesNo.Y);
    if (bucket == null) {
      throw new WaverException(ResultCode.NOT_FOUND);
    }

    Long categoryId = categoryRepository.findIdByUserIdAndDefaultYn(userId, YesNo.Y);

    Bucket scraped = bucketRepository.save(Bucket.copy(bucket, userId, categoryId));
    return scraped.getId();
  }
}
