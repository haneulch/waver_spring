package com.mybury.waver.event;

import com.mybury.waver.common.code.ReportType;
import com.mybury.waver.event.message.CommentReportedEvent;
import com.mybury.waver.repository.CommentRepository;
import com.mybury.waver.repository.ReportRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 댓글 신고 이벤트.
 * 누적 신고가 임계값 이상이면 댓글을 차단(isBlocked=Y)하여 모든 사용자에게 미노출한다.
 */
@Component
@RequiredArgsConstructor
public class CommentReportedEventListener {

  private static final long REPORT_COUNT_THRESHOLD = 3;

  private final ReportRepository reportRepository;
  private final CommentRepository commentRepository;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handle(CommentReportedEvent event) {
    long commentId = event.commentId();
    long reportCount = reportRepository.countByCommentIdAndReportType(commentId, ReportType.COMMENT);

    if (reportCount >= REPORT_COUNT_THRESHOLD) {
      commentRepository.markBlockedById(commentId);
    }
  }
}
