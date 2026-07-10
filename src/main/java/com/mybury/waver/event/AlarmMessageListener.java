package com.mybury.waver.event;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
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
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
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

  @EventListener
  public void handle(AlarmMessageEvent event) {
    long userId = event.userId();
    AlarmMessageType type = event.type();

    if (event.otherUserId() != null && userId == event.otherUserId()) {
      return;
    }

    User user = userService.getUserOnlyById(userId);
    Locale locale = user.getLocale();

    String otherUserName = event.otherUserId() != null
        ? userService.getUserNameById(event.otherUserId()) : null;

    String message = switch (type) {
      case NOTICE -> alarmMessageParser.parse(type, locale);
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
      Alarm alarm = Alarm.builder().userId(userId).message(message).build();
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
    } catch (FirebaseMessagingException e) {
      log.warn("Failed to send fcm.", e);
    }
  }
}