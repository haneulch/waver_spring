package com.mybury.waver.service;

import com.mybury.waver.common.code.ReportType;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.LikeBucket;
import com.mybury.waver.domain.Report;
import com.mybury.waver.domain.UserKeyword;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.LikeBucketRepository;
import com.mybury.waver.repository.ReportRepository;
import com.mybury.waver.repository.UserKeywordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {
  private final BucketRepository bucketRepository;
  private final ReportRepository reportRepository;
  private final LikeBucketRepository likeBucketRepository;
  private final UserKeywordRepository userKeywordRepository;

  public void report(long id, String reason) {
    Report report = Report.builder()
        .reportType(ReportType.BUCKET)
        .bucketlistId(id)
        .reason(reason)
        .build();
    reportRepository.save(report);
  }

  public List<Bucket> feeds(Long userId) {
    return bucketRepository.findFeed();
  }

  @Transactional
  public void like(long userId, long id) {
    boolean isLiked = likeBucketRepository.existsByUserIdAndBucketId(userId, id);
    bucketRepository.updateLike(id, isLiked ? -1 : 1);

    if (isLiked) {
      likeBucketRepository.deleteByUserIdAndBucketId(userId, id);
    } else {
      LikeBucket likeBucket = LikeBucket.builder().userId(userId).bucketId(id).build();
      likeBucketRepository.save(likeBucket);
    }
  }

  @Transactional
  public void keyword(long userId, List<Integer> keywordIds) {
    List<UserKeyword> keywords = keywordIds.stream().map(id -> UserKeyword.builder().userId(userId).keywordId(id).build()).toList();
    userKeywordRepository.saveAll(keywords);
  }
}
