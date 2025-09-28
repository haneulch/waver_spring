package com.mybury.waver.service;

import com.mybury.waver.common.code.ReportType;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.LikeBucket;
import com.mybury.waver.domain.Report;
import com.mybury.waver.domain.UserKeyword;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.LikeBucketRepository;
import com.mybury.waver.repository.ReportRepository;
import com.mybury.waver.repository.UserKeywordRepository;
import com.mybury.waver.web.message.v1.feed.FeedResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

  public void report(long id, String reason) {
    Report report = Report.builder()
      .reportType(ReportType.BUCKET)
      .bucketlistId(id)
      .reason(reason)
      .build();
    reportRepository.save(report);
  }

  public List<FeedResponse> feeds(Long userId) {
    List<Bucket> buckets = bucketRepository.findFeed();

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
        return FeedResponse.of(bucket, likeYn);
      })
      .toList();
  }

  @Transactional
  public void keyword(long userId, List<Integer> keywordIds) {
    List<UserKeyword> keywords = keywordIds.stream()
      .map(id -> UserKeyword.builder().userId(userId).keywordId(id).build()).toList();
    userKeywordRepository.saveAll(keywords);
  }

  public long copy(long userId, long id) {
    // TODO: copy bucket
    return 0L;
  }
}
