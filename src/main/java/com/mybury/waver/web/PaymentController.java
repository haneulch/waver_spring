package com.mybury.waver.web;

import com.mybury.waver.service.google.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

  private final PurchaseService purchaseService;

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
