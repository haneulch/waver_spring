package com.mybury.waver.service.google;

import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.model.SubscriptionPurchaseV2;
import com.mybury.waver.common.code.ResultCode;
import com.mybury.waver.exception.WaverException;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubscriptionService {

  private static final String SUBSCRIPTION_STATE_ACTIVE = "SUBSCRIPTION_STATE_ACTIVE";

  private final AndroidPublisher androidPublisher;

  @Value("${google.play.package-name}")
  private String packageName;

  /**
   * 구글 서버에서 구독 상태를 조회한다.
   *
   * @return 구독이 활성 상태면 true
   * @throws WaverException 구글 API 호출에 실패한 경우 (상태 판별 불가)
   */
  public boolean verifySubscription(String purchaseToken) {
    try {
      // 구독형(Subscription) 검증 API 호출
      SubscriptionPurchaseV2 subscription = androidPublisher.purchases().subscriptionsv2()
          .get(packageName, purchaseToken)
          .execute();

      String subscriptionState = subscription.getSubscriptionState();

      if (SUBSCRIPTION_STATE_ACTIVE.equals(subscriptionState)) {
        log.info("구독 인증 성공! 만료일자: {}", extractExpiryTime(subscription));
        return true;
      }

      log.info("활성화되지 않은 구독 상태입니다: {}", subscriptionState);
      return false;

    } catch (IOException e) {
      // 통신 실패는 '비활성'과 구분해야 하므로 예외로 전파 (RTDN 재시도 대상)
      log.error("구독 정보 조회 중 에러 발생: {}", e.getMessage(), e);
      throw new WaverException(ResultCode.INTERNAL_SERVER_ERROR);
    }
  }

  private String extractExpiryTime(SubscriptionPurchaseV2 subscription) {
    List<com.google.api.services.androidpublisher.model.SubscriptionPurchaseLineItem> lineItems =
        subscription.getLineItems();
    if (lineItems == null || lineItems.isEmpty()) {
      return null;
    }
    return lineItems.get(0).getExpiryTime();
  }
}
