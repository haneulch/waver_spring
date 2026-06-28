package com.mybury.waver.service.google;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class PlayStoreRtdnSubscriber {

  private final PubSubTemplate pubSubTemplate;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Value("${spring.cloud.gcp.pubsub.subscription-id}")
  private String subscriptionId;

  public PlayStoreRtdnSubscriber(PubSubTemplate pubSubTemplate) {
    this.pubSubTemplate = pubSubTemplate;
  }

  // 애플리케이션이 구동 완료되면 구글 큐(Queue) 리스닝 시작
  @EventListener(ApplicationReadyEvent.class)
  public void subscribeToRtdn() {
    pubSubTemplate.subscribe(subscriptionId, (BasicAcknowledgeablePubsubMessage message) -> {
      try {
        // 1. 구글이 보낸 페이로드 추출 (Base64 자동 디코딩된 문자열 형태)
        String payload = message.getPubsubMessage().getData().toStringUtf8();
        JsonNode rootNode = objectMapper.readTree(payload);

        System.out.println("📥 구글 플레이 RTDN 수신 성공!");

        // 2. 테스트 알림인지 실제 결제 변동인지 분기 처리
        if (rootNode.has("testNotification")) {
          System.out.println("⚙️ 구글 콘솔에서 보낸 테스트 신호입니다.");
        } else if (rootNode.has("subscriptionNotification")) {
          // 🚨 정기 구독 관련 변동이 온 경우 (자동갱신, 취소, 만료 등)
          JsonNode subNode = rootNode.get("subscriptionNotification");
          String purchaseToken = subNode.get("purchaseToken").asText();
          int notificationType = subNode.get("notificationType").asInt();

          System.out.println("🔔 구독 상태 변경 감지! 토큰: " + purchaseToken + " / 타입: " + notificationType);

          // TODO: 이전에 만들어 둔 'verifySubscription(userId, purchaseToken)'을
          // 호출하여 구글 서버의 최신 상세 상태를 재조회한 뒤 내 DB를 업데이트합니다.
        }

        // 3. 메시지 처리 완료 알림 (큐에서 메시지 제거 - 안 하면 구글이 계속 다시 보냄)
        message.ack();

      } catch (Exception e) {
        System.err.println("RTDN 처리 중 에러 발생: " + e.getMessage());
        // 처리 실패 시 큐에 그대로 남겨두어 나중에 재시도하게 만듦
        message.nack();
      }
    });
  }
}
