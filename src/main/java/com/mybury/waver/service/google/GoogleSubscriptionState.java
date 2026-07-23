package com.mybury.waver.service.google;

import java.time.LocalDateTime;

/**
 * 구글 구독 조회 결과. RTDN 동기화에서 상태 분기와 만료일 갱신에 사용한다.
 *
 * @param rawState   구글 SubscriptionPurchaseV2.subscriptionState 원본 값
 * @param expiryTime 현재 결제 주기의 만료 시각(없으면 null)
 */
public record GoogleSubscriptionState(String rawState, LocalDateTime expiryTime) {

  private static final String STATE_ACTIVE = "SUBSCRIPTION_STATE_ACTIVE";
  private static final String STATE_CANCELED = "SUBSCRIPTION_STATE_CANCELED";

  /** 자동 갱신 활성 상태 */
  public boolean isActive() {
    return STATE_ACTIVE.equals(rawState);
  }

  /** 자동 갱신은 해제됐으나 만료일까지는 접근이 유지되는 상태 */
  public boolean isCanceled() {
    return STATE_CANCELED.equals(rawState);
  }
}
