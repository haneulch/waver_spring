package com.mybury.waver.event;

import com.mybury.waver.common.code.ReportType;
import com.mybury.waver.event.message.BucketReportedEvent;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.ReportRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 버킷 신고 이벤트
 */
@Component
@RequiredArgsConstructor
public class BucketReportedEventListener {

  private final ReportRepository reportRepository;
  private final BucketRepository bucketRepository;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handle(BucketReportedEvent event) {
    long bucketId = event.bucketId();
    long reportCount = reportRepository.countByBucketlistIdAndReportType(bucketId, ReportType.BUCKET);

    if (reportCount >= 3) {
      bucketRepository.markDeletedById(bucketId);
    }
  }
}
