package com.mybury.waver.event;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.mybury.waver.alarm.AlarmMessageParser;
import com.mybury.waver.alarm.code.AlarmMessageType;
import com.mybury.waver.domain.Alarm;
import com.mybury.waver.domain.User;
import com.mybury.waver.event.message.AlarmMessageEvent;
import com.mybury.waver.service.AlarmService;
import com.mybury.waver.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.StringUtils;

import java.util.Locale;

/**
 * 알람 메시지 리스너
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmMessageListener {

  private final UserService userService;
  private final AlarmService alarmService;
  private final AlarmMessageParser alarmMessageParser;

  // 발행 트랜잭션 커밋 후 별도 트랜잭션에서 실행한다.
  // (같은 트랜잭션에서 동기 실행하면 알람/푸시 실패가 원래 작업(좋아요, 댓글 저장)까지 롤백시킨다)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener
  public void handle(AlarmMessageEvent event) {
    long userId = event.userId();
    AlarmMessageType type = event.type();

    if (event.otherUserId() != null && userId == event.otherUserId()) {
      return;
    }

    User user = userService.getUserOnlyById(userId);
    if (user == null) {
      log.warn("User not found for alarm event: {}", event);
      return;
    }
    Locale locale = user.getLocale();

    // 알림을 유발한 상대(좋아요/댓글/팔로우/함께하기 주체). 이름과 프로필 이미지를 함께 확보한다.
    User otherUser = event.otherUserId() != null
        ? userService.getUserOnlyById(event.otherUserId()) : null;
    String otherUserName = otherUser != null ? otherUser.getName() : null;

    String message = switch (type) {
      case NOTICE -> alarmMessageParser.parse(type, locale);
      // 이벤트 본문을 그대로 전달한다.
      case EVENT -> {
        if (StringUtils.hasText(event.message())) {
          yield alarmMessageParser.parse(type, locale, event.message());
        }
        log.warn("Event content is null for event: {}", event);
        yield null;
      }
      case FEED_COMMENT -> {
        if (StringUtils.hasText(otherUserName)) {
          yield alarmMessageParser.parse(type, locale, otherUserName);
        }
        log.warn("Other user is null for event: {}", event);
        yield null;
      }
      // OOO님이 회원님의 버킷리스트를 좋아합니다.
      case FEED_LIKE -> {
        if (StringUtils.hasText(otherUserName)) {
          yield alarmMessageParser.parse(type, locale, otherUserName);
        }
        log.warn("Other user is null for event: {}", event);
        yield null;
      }
      // OOO님이 회원님을 팔로우하기 시작했습니다.
      case FOLLOW -> {
        if (StringUtils.hasText(otherUserName)) {
          yield alarmMessageParser.parse(type, locale, otherUserName);
        }
        log.warn("Other user is null for event: {}", event);
        yield null;
      }
      // OOO 뱃지를 획득했습니다.
      case BADGE -> {
        if (StringUtils.hasText(event.message())) {
          yield alarmMessageParser.parse(type, locale, event.message());
        }
        log.warn("Badge title is null for event: {}", event);
        yield null;
      }
      // OOO님이 함께하는 버킷 "제목"을(를) 완성했습니다.
      case TOGETHER -> {
        if (StringUtils.hasText(otherUserName) && StringUtils.hasText(event.bucketTitle())) {
          yield alarmMessageParser.parse(type, locale, otherUserName, event.bucketTitle());
        }
        log.warn("Other user or bucket title is null for event: {}", event);
        yield null;
      }
      // OOO님이 함께하는 버킷 "제목"에 회원님을 초대했습니다.
      case TOGETHER_INVITE -> {
        if (StringUtils.hasText(otherUserName) && StringUtils.hasText(event.bucketTitle())) {
          yield alarmMessageParser.parse(type, locale, otherUserName, event.bucketTitle());
        }
        log.warn("Other user or bucket title is null for event: {}", event);
        yield null;
      }
      // 디데이가 7일 남은 버킷리스트가 있습니다.\n:버킷리스트제목
      case D_DAY_7 -> {
        if (StringUtils.hasText(event.bucketTitle())) {
          yield alarmMessageParser.parse(type, locale, event.bucketTitle());
        }
        log.warn("Bucket title is null for event: {}", event);
        yield null;
      }
    };

    if (StringUtils.hasText(message)) {
      // 팔로우 알림에는 팔로워의 프로필 이미지를 함께 저장한다.
      String imgUrl = type == AlarmMessageType.FOLLOW && otherUser != null
          ? otherUser.getImgUrl() : null;
      Alarm alarm = Alarm.builder()
          .userId(userId)
          .type(type.getPushType())
          .message(message)
          .imgUrl(imgUrl)
          .bucketId(event.bucketId())
          .build();
      alarmService.create(alarm);
      if (StringUtils.hasText(user.getFcmToken())) {
        sendPush(user.getFcmToken(), message);
      }
    }
  }

  private void sendPush(String targetToken, String body) {

    Message message = Message.builder()
        .setToken(targetToken)
        .setNotification(Notification.builder()
            .setBody(body)
            .build())
        .build();

    // 4. FCM 서버로 발송요청 (비동기로 하려면 sendAsync 사용)
    try {
      String response = FirebaseMessaging.getInstance().send(message);
      log.info("Successfully sent message: {}", response);
    } catch (Exception e) {
      // Firebase 미초기화(IllegalStateException) 등 어떤 실패도 알람 저장에 영향 주지 않는다
      log.warn("Failed to send fcm.", e);
    }
  }
}