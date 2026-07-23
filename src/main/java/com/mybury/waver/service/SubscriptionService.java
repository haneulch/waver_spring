package com.mybury.waver.service;

import com.mybury.waver.common.code.BillingCycle;
import com.mybury.waver.common.code.PremiumStatus;
import com.mybury.waver.common.code.ResultCode;
import com.mybury.waver.common.code.SubscriptionStatus;
import com.mybury.waver.domain.Subscribe;
import com.mybury.waver.domain.User;
import com.mybury.waver.exception.WaverException;
import com.mybury.waver.repository.SubscribeRepository;
import com.mybury.waver.repository.UserRepository;
import com.mybury.waver.service.google.GooglePlaySubscriptionService;
import com.mybury.waver.service.google.GoogleSubscriptionState;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {

  private final SubscribeRepository subscribeRepository;
  private final UserRepository userRepository;
  private final GooglePlaySubscriptionService googlePlaySubscriptionService;

  /**
   * 클라이언트가 보낸 구매 토큰을 구글에 검증한 뒤 구독을 시작하고 프리미엄을 활성화한다.
   */
  @Transactional
  public Subscribe start(Long userId, BillingCycle billingCycle, String subscribeId) {
    if (!googlePlaySubscriptionService.verifySubscription(subscribeId)) {
      throw new WaverException(ResultCode.BAD_REQUEST);
    }

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));
    user.setPremiumStatus(PremiumStatus.ACTIVE);

    LocalDateTime now = LocalDateTime.now();
    Subscribe subscription = Subscribe.builder()
        .userId(userId)
        .subscribeId(subscribeId)
        .billingCycle(billingCycle)
        .status(SubscriptionStatus.ACTIVE)
        .startAt(now)
        .expiredAt(billingCycle.nextBillingDate(now))
        .build();
    return subscribeRepository.save(subscription);
  }

  /**
   * RTDN 수신 시 구매 토큰으로 구글 최신 상태를 재조회해 구독 정보와 사용자 프리미엄 상태를 동기화한다.
   */
  @Transactional
  public void syncSubscriptionState(String purchaseToken) {
    Subscribe subscribe = subscribeRepository.findTopBySubscribeIdOrderByIdDesc(purchaseToken)
        .orElse(null);
    if (subscribe == null) {
      // 우리 서비스에서 등록된 적 없는 토큰 - 재시도해도 소용없으므로 로그만 남긴다
      log.warn("구매 토큰에 해당하는 구독 정보가 없습니다: {}", purchaseToken);
      return;
    }

    GoogleSubscriptionState state = googlePlaySubscriptionService.getSubscriptionState(purchaseToken);
    LocalDateTime now = LocalDateTime.now();

    PremiumStatus premiumStatus;
    if (state.isActive()) {
      subscribe.renew(state.expiryTime());
      premiumStatus = PremiumStatus.ACTIVE;
    } else if (state.isCanceled()) {
      // Play 스토어에서 자동 갱신 해제 - 만료일까지는 프리미엄 유지
      subscribe.markCancelled(now, state.expiryTime());
      premiumStatus = PremiumStatus.ACTIVE;
    } else {
      // 만료·보류·일시중지·환불 취소 등 - 접근 종료
      subscribe.expire();
      premiumStatus = PremiumStatus.EXPIRED;
    }

    userRepository.findById(subscribe.getUserId())
        .ifPresent(user -> user.setPremiumStatus(premiumStatus));
  }
}
