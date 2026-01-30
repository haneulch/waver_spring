package com.mybury.waver.event;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.mybury.waver.alarm.AlarmMessageParser;
import com.mybury.waver.alarm.code.AlarmMessageType;
import com.mybury.waver.domain.User;
import com.mybury.waver.event.message.AlarmMessageEvent;
import com.mybury.waver.repository.UserRepository;
import com.mybury.waver.service.AlarmService;
import com.mybury.waver.service.UserService;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 알람 메시지 리스너
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmMessageListener {

  private final UserService userService;
  private final UserRepository userRepository;
  private final AlarmService alarmService;
  private final AlarmMessageParser alarmMessageParser;

  @EventListener
  public void handleAlarmMessageEvent(AlarmMessageEvent event) {
    // 1. 알림을 받을 유저 조회
    User user = userRepository.findById(event.userId())
        .orElseThrow(() -> new RuntimeException("User not found"));

    // 2. DB에 저장된 fcmToken 확인
    String token = user.getFcmToken();

    if (token == null || token.isEmpty()) {
      log.info("사용자의 FCM 토큰이 없어 푸시를 발송하지 않습니다. userId: {}", user.getId());
      return;
    }

    String otherUserName = event.otherUserId() != null
        ? userService.getUserNameById(event.otherUserId()) : null;

    // 3. FCM 메시지 구성
    Notification notification = Notification.builder()
        .setTitle("Waver 알림")
        .setBody(getParsedMessage(user.getLocale(), otherUserName, event))
        .build();

    Message message = Message.builder()
        .setToken(token)
        .setNotification(notification)
        .putData("click_action", "OPEN_APP") // 앱 내 특정 화면 이동 등에 활용 가능
        .build();

    // 4. 실제 발송
    try {
      String response = FirebaseMessaging.getInstance().send(message);
      log.info("FCM 발송 성공: {}", response);
    } catch (Exception e) {
      log.error("FCM 발송 실패: {}", e.getMessage());
    }
  }

  private String getParsedMessage(Locale locale, String otherUserName, AlarmMessageEvent event) {
    AlarmMessageType type = event.type();
    return switch (type) {
      case NOTICE -> alarmMessageParser.parse(type, locale);
      case FEED_COMMENT -> {
        if (StringUtils.hasText(otherUserName)) {
          yield alarmMessageParser.parse(type, locale, otherUserName);
        }
        yield null;
      }
      // OOO님이 회원님의 버킷리스트를 좋아합니다.
      case FEED_LIKE -> {
        if (StringUtils.hasText(otherUserName)) {
          yield alarmMessageParser.parse(type, locale, otherUserName);
        }
        yield null;
      }
      // 디데이가 7일 남은 버킷리스트가 있습니다.\n:버킷리스트제목
      case D_DAY_7 -> {
        if (StringUtils.hasText(event.bucketTitle())) {
          yield alarmMessageParser.parse(type, locale, event.bucketTitle());
        }
        yield null;
      }
    };
  }
}