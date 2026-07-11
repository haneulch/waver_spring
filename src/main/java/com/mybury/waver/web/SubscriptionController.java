package com.mybury.waver.web;

import com.mybury.waver.annotation.UserId;
import com.mybury.waver.domain.Subscribe;
import com.mybury.waver.service.SubscriptionService;
import com.mybury.waver.web.message.v1.subscription.SubscriptionResponse;
import com.mybury.waver.web.message.v1.subscription.SubscriptionStartRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "구독(Waver+)", description = "구글 인앱 구독 시작·취소. 상태 변경은 RTDN으로 자동 동기화됩니다.")
@RestController
@RequestMapping("waver/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

  private final SubscriptionService subscriptionService;

  @Operation(summary = "구독 시작",
      description = "구글 결제 완료 후 받은 purchaseToken(subscribeId)을 서버가 구글에 검증하고, "
          + "통과하면 구독을 등록하고 프리미엄(ACTIVE)을 활성화합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "4000", description = "BAD_REQUEST — 구글 검증 실패(유효하지 않은 토큰/비활성 구독)")
  })
  @PostMapping("start")
  public SubscriptionResponse start(
      @Parameter(hidden = true) @UserId Long userId,
      @Valid @RequestBody SubscriptionStartRequest request) {
    Subscribe subscription = subscriptionService.start(userId, request.billingCycle(), request.subscribeId());
    return SubscriptionResponse.from(subscription);
  }

  @Operation(summary = "구독 취소",
      description = "구독을 취소 대기(PENDING_CANCELLATION) 상태로 전환합니다. 프리미엄 혜택은 만료일까지 유지됩니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "9100", description = "SUBSCRIPTION_NOT_FOUND — 활성 구독 없음"),
      @ApiResponse(responseCode = "9101", description = "SUBSCRIPTION_ALREADY_CANCELLED — 이미 취소 대기 중")
  })
  @PostMapping("cancel")
  public SubscriptionResponse cancel(@Parameter(hidden = true) @UserId Long userId) {
    Subscribe subscription = subscriptionService.cancel(userId);
    return SubscriptionResponse.from(subscription);
  }
}
