package com.mybury.waver.service.google;

import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.model.SubscriptionPurchaseV2;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SubscriptionService {

  private final AndroidPublisher androidPublisher;

  @Value("${google.play.package-name}")
  private String packageName;

  @Transactional
  public void verifySubscription(Long userId, String purchaseToken) {
    try {
      // 🚨 구독형(Subscription) 검증 API 호출
      SubscriptionPurchaseV2 subscription = androidPublisher.purchases().subscriptionsv2()
          .get(packageName, purchaseToken)
          .execute();

      // 1. 현재 구독의 상태 확인 (SUBSCRIPTION_STATE_ACTIVE = 활성화됨)
      String subscriptionState = subscription.getSubscriptionState();

      // 2. 만료 시간 확인 (유닉스 타임스탬프 문자열로 리턴됨)
      String expiryTime = subscription.getLineItems().get(0).getExpiryTime();

      // 3. 자동 갱신 여부 확인 (기본값: AcknowledgementState)
      String acknowledgmentState = subscription.getAcknowledgementState();

      if ("SUBSCRIPTION_STATE_ACTIVE".equals(subscriptionState)) {
        // ➡️ [성공] 유저의 구독 등급 권한을 DB에 반영하고 만료일을 기록합니다.
        // 예: userSubscriptionRepository.save(new UserSubscription(userId, expiryTime, true));
        System.out.println("구독 인증 성공! 만료일자: " + expiryTime);
      } else {
        System.out.println("활성화되지 않은 구독 상태입니다: " + subscriptionState);
      }

    } catch (Exception e) {
      System.err.println("구독 정보 조회 중 에러 발생: " + e.getMessage());
    }
  }
}
