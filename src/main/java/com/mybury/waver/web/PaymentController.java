package com.mybury.waver.web;

import com.mybury.waver.service.google.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "결제", description = "구글 인앱 일회성 결제 검증")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

  private final PurchaseService purchaseService;

  @Operation(summary = "구글 결제 검증",
      description = "일회성 상품 구매 영수증(purchaseToken)을 구글 서버에 검증합니다. "
          + "주의: /waver 경로 밖이라 현재 토큰 인증이 적용되지 않는 엔드포인트입니다.")
  @PostMapping("/google/verify")
  public ResponseEntity<String> verifyPayment(
      @RequestParam Long userId,
      @RequestBody Map<String, String> request) {

    String productId = request.get("productId");
    String purchaseToken = request.get("purchaseToken");

    boolean isSuccess = purchaseService.verifyAndProcessPurchase(userId, productId, purchaseToken);

    if (isSuccess) {
      return ResponseEntity.ok("Payment verified and processed successfully.");
    } else {
      return ResponseEntity.badRequest().body("Invalid payment or verification failed.");
    }
  }
}
