package com.mybury.waver.service.google;

import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.model.SubscriptionPurchaseV2;
import com.mybury.waver.common.code.ResultCode;
import com.mybury.waver.exception.WaverException;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class GooglePlaySubscriptionService {

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

  /**
   * 구글 서버에서 구독 상태와 만료 시각을 조회한다. RTDN 동기화에서 상태 분기에 사용한다.
   *
   * @throws WaverException 구글 API 호출에 실패한 경우 (RTDN 재시도 대상)
   */
  public GoogleSubscriptionState getSubscriptionState(String purchaseToken) {
    try {
      SubscriptionPurchaseV2 subscription = androidPublisher.purchases().subscriptionsv2()
          .get(packageName, purchaseToken)
          .execute();

      String state = subscription.getSubscriptionState();
      LocalDateTime expiry = parseExpiryTime(extractExpiryTime(subscription));
      log.info("구독 상태 조회: state={}, 만료일={}", state, expiry);
      return new GoogleSubscriptionState(state, expiry);

    } catch (IOException e) {
      log.error("구독 상태 조회 중 에러 발생: {}", e.getMessage(), e);
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

  /** 구글의 RFC3339 시각 문자열을 서버 시간대 기준 LocalDateTime으로 변환한다. */
  private LocalDateTime parseExpiryTime(String rfc3339) {
    if (rfc3339 == null) {
      return null;
    }
    try {
      return LocalDateTime.ofInstant(Instant.parse(rfc3339), ZoneId.systemDefault());
    } catch (DateTimeParseException e) {
      log.warn("만료 시각 파싱 실패: {}", rfc3339);
      return null;
    }
  }
}
