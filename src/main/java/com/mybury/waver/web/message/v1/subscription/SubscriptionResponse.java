package com.mybury.waver.web.message.v1.subscription;

import com.mybury.waver.common.code.BillingCycle;
import com.mybury.waver.common.code.SubscriptionStatus;
import com.mybury.waver.domain.Subscribe;
import java.time.LocalDateTime;

public record SubscriptionResponse(
    Long id,
    BillingCycle billingCycle,
    SubscriptionStatus status,
    LocalDateTime startAt,
    LocalDateTime expiredAt,
    LocalDateTime cancelledAt
) {
  public static SubscriptionResponse from(Subscribe s) {
    return new SubscriptionResponse(
        s.getId(),
        s.getBillingCycle(),
        s.getStatus(),
        s.getStartAt(),
        s.getExpiredAt(),
        s.getCancelledAt()
    );
  }
}
