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

@Tag(name = "구독")
@RestController
@RequestMapping("waver/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

  private final SubscriptionService subscriptionService;

  @Operation(summary = "구독 시작")
  @ApiResponses({
      @ApiResponse(responseCode = "2000", description = "SUCCESS")
  })
  @PostMapping("start")
  public SubscriptionResponse start(
      @Parameter(hidden = true) @UserId Long userId,
      @Valid @RequestBody SubscriptionStartRequest request) {
    Subscribe subscription = subscriptionService.start(userId, request.billingCycle(), request.subscribeId());
    return SubscriptionResponse.from(subscription);
  }

  @Operation(summary = "구독 취소")
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
