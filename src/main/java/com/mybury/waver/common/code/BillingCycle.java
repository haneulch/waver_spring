package com.mybury.waver.common.code;

import java.time.LocalDateTime;

public enum BillingCycle {
  MONTHLY, YEARLY;

  public LocalDateTime nextBillingDate(LocalDateTime from) {
    return switch (this) {
      case MONTHLY -> from.plusMonths(1);
      case YEARLY  -> from.plusYears(1);
    };
  }
}
