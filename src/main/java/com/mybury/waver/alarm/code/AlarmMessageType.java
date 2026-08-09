package com.mybury.waver.alarm.code;

import com.mybury.waver.common.code.PushType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmMessageType {
  NOTICE("alert.notice", PushType.NOTICE), // 공지사항
  EVENT("alert.event", PushType.EVENT), // 이벤트
  FEED_LIKE("alert.like", PushType.LIKE), // 피드 좋아요
  FEED_COMMENT("alert.comment", PushType.COMMENT), // 피드 댓글
  FOLLOW("alert.follow", PushType.FOLLOW), // 팔로우
  BADGE("alert.badge", PushType.BADGE), // 뱃지 달성
  TOGETHER("alert.together", PushType.TOGETHER), // 함께하는 사람이 버킷 완성
  TOGETHER_INVITE("alert.together.invite", PushType.TOGETHER), // 함께하는 버킷에 초대됨
  D_DAY_7("alert.7day", PushType.D_DAY), // 7일 남은 버킷 알림
  ;

  private final String code;
  private final PushType pushType;
}
