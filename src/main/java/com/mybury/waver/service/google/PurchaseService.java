package com.mybury.waver.service.google;

import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.model.ProductPurchase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PurchaseService {

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
      // purchaseState: 0 = 결제 완료, 1 = 취소됨, 2 = 대기 중
      Integer purchaseState = purchase.getPurchaseState();

      if (purchaseState != null && purchaseState == 0) {
        // 3. [성공] 중복 결제 검증 및 내 DB에 결제 내역 저장 로직 작성 기재
        // 예: paymentRepository.save(new Payment(userId, productId, purchaseToken));

        System.out.println("결제 검증 성공! 상품 ID: " + productId);
        return true;
      } else {
        System.out.println("결제 검증 실패 (취소되었거나 대기 중인 결제): " + purchaseState);
        return false;
      }

    } catch (Exception e) {
      // 구글 서버 통신 오류 또는 올바르지 않은 토큰일 때 예외 발생
      System.err.println("구글 API 호출 중 에러 발생: " + e.getMessage());
      return false;
    }
  }
}
