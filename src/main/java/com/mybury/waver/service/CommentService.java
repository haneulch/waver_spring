package com.mybury.waver.service;

import com.mybury.waver.common.code.ReportType;
import com.mybury.waver.common.code.ResultCode;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.Comment;
import com.mybury.waver.domain.Report;
import com.mybury.waver.event.message.AlarmMessageEvent;
import com.mybury.waver.event.message.CommentReportedEvent;
import com.mybury.waver.exception.WaverException;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.CommentRepository;
import com.mybury.waver.repository.ReportRepository;
import com.mybury.waver.web.message.v1.comment.CommentCreateRequest;
import com.mybury.waver.web.message.v1.comment.CommentUpdateRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final BucketRepository bucketRepository;
  private final ReportRepository reportRepository;
  private final ApplicationEventPublisher publisher;

  @Transactional
  public void commentCreate(Long userId, @Valid CommentCreateRequest request) {
    // 버킷 존재 확인
    long bucketUserId = bucketRepository.findById(request.bucketId()).map(Bucket::getUserId)
        .orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));

    // 코멘트 생성
    String mentionIds = request.mentionIds() != null ? String.join(",", request.mentionIds()) : null;
    Comment comment = Comment.builder()
        .userId(userId)
        .comment(request.content())
        .bucketId(request.bucketId())
        .mentionIds(mentionIds).build();
    commentRepository.save(comment);

    publisher.publishEvent(AlarmMessageEvent.feedComment(bucketUserId, userId, request.bucketId()));
  }

  @Transactional
  public void commentDelete(Long id, Long userId) {
    Comment comment = commentRepository.findByIdAndUserId(id, userId)
        .orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));
    commentRepository.delete(comment);
  }

  @Transactional
  public void commentUpdate(Long id, Long userId, @Valid CommentUpdateRequest request) {
    Comment comment = commentRepository.findByIdAndUserId(id, userId)
        .orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));

    if (request.mentionIds() != null) {
      String mentionIds = String.join(",", request.mentionIds());
      comment.setMentionIds(mentionIds);
    }
    comment.setComment(request.content());

    commentRepository.save(comment);
  }

  @Transactional
  public void commentHide(Long id, Long userId) {
    Comment comment = commentRepository.findByIdAndUserId(id, userId)
        .orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));
    comment.setIsHide(YesNo.Y);
    commentRepository.save(comment);
  }

  @Transactional
  public void report(Long id, String reason, Long userId) {
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));
    if (comment.getUserId() != null && comment.getUserId().equals(userId)) {
      throw new WaverException(ResultCode.FORBIDDEN);
    }

    boolean isDuplicated = reportRepository.existsByReportUserIdAndCommentIdAndReportType(userId, id,
        ReportType.COMMENT);
    if (isDuplicated) {
      return;
    }

    Report report = Report.builder()
        .reportType(ReportType.COMMENT)
        .commentId(id)
        .reason(reason)
        .reportUserId(userId)
        .build();
    reportRepository.save(report);

    publisher.publishEvent(new CommentReportedEvent(id));
  }
}
