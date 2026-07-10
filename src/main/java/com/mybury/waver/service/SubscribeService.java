package com.mybury.waver.service;

import com.mybury.waver.common.code.PremiumStatus;
import com.mybury.waver.common.code.ResultCode;
import com.mybury.waver.domain.Subscribe;
import com.mybury.waver.domain.User;
import com.mybury.waver.exception.WaverException;
import com.mybury.waver.repository.SubscribeRepository;
import com.mybury.waver.repository.UserRepository;
import com.mybury.waver.service.google.SubscriptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscribeService {

  private final SubscribeRepository subscribeRepository;
  private final UserRepository userRepository;
  private final SubscriptionService subscriptionService;

  /**
   * 클라이언트가 보낸 구매 토큰을 구글에 검증한 뒤 구독을 등록하고 프리미엄을 활성화한다.
   */
  @Transactional
  public void subscribe(long userId, String subscribeId) {
    if (!subscriptionService.verifySubscription(subscribeId)) {
      throw new WaverException(ResultCode.BAD_REQUEST);
    }

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));
    user.setPremiumStatus(PremiumStatus.ACTIVE);

    Subscribe subscribe = Subscribe.builder()
        .userId(userId)
        .subscribeId(subscribeId)
        .build();
    subscribeRepository.save(subscribe);
  }

  /**
   * RTDN 수신 시 구매 토큰으로 구글 최신 상태를 재조회해 사용자 프리미엄 상태를 동기화한다.
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

    boolean active = subscriptionService.verifySubscription(purchaseToken);

    userRepository.findById(subscribe.getUserId())
        .ifPresent(user -> user.setPremiumStatus(active ? PremiumStatus.ACTIVE : PremiumStatus.EXPIRED));
  }
}
