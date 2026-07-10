package com.mybury.waver.service.google;

import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.model.ProductPurchase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PurchaseService {

  // purchaseState: 0 = 결제 완료, 1 = 취소됨, 2 = 대기 중
  private static final int PURCHASE_STATE_PURCHASED = 0;

  private final AndroidPublisher androidPublisher;

  @Value("${google.play.package-name}")
  private String packageName;

  @Transactional
  public boolean verifyAndProcessPurchase(Long userId, String productId, String purchaseToken) {
    try {
      // 1. 구글 서버에 영수증 조회 요청
      ProductPurchase purchase = androidPublisher.purchases().products()
          .get(packageName, productId, purchaseToken)
          .execute();

      // 2. 결제 상태 확인
      Integer purchaseState = purchase.getPurchaseState();

      if (purchaseState != null && purchaseState == PURCHASE_STATE_PURCHASED) {
        // 3. [성공] 중복 결제 검증 및 내 DB에 결제 내역 저장 로직 작성 기재
        // 예: paymentRepository.save(new Payment(userId, productId, purchaseToken));

        log.info("결제 검증 성공! 상품 ID: {}", productId);
        return true;
      } else {
        log.info("결제 검증 실패 (취소되었거나 대기 중인 결제): {}", purchaseState);
        return false;
      }

    } catch (Exception e) {
      // 구글 서버 통신 오류 또는 올바르지 않은 토큰일 때 예외 발생
      log.error("구글 API 호출 중 에러 발생: {}", e.getMessage(), e);
      return false;
    }
  }
}
