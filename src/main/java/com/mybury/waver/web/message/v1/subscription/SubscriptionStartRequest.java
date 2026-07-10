package com.mybury.waver.web.message.v1.subscription;

import com.mybury.waver.common.code.BillingCycle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubscriptionStartRequest(
    @NotNull BillingCycle billingCycle,

    @NotBlank
    @Schema(description = "구글 결제 구매 토큰(purchaseToken)")
    String subscribeId
) {
}
