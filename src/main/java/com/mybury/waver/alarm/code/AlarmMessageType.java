package com.mybury.waver.alarm.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmMessageType {
  NOTICE("alert.notice"), // 공지사항
  FEED_LIKE("alert.comment"), // 피드 좋아요
  FEED_COMMENT("alert.like"), // 피드 댓글
  D_DAY_7("alert.7day"), // 7일 남은 버킷 알림
  ;

  private final String code;
}
