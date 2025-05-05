package com.mybury.waver.service;

import com.mybury.waver.common.code.ReportType;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.Report;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {
  private final BucketRepository bucketRepository;
  private final ReportRepository reportRepository;

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
}
