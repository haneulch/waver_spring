package com.mybury.waver.domain;

import com.mybury.waver.common.code.BillingCycle;
import com.mybury.waver.common.code.SubscriptionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Subscribe extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long userId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private BillingCycle billingCycle;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 25)
  private SubscriptionStatus status;

  @Column(nullable = false)
  private LocalDateTime startAt;

  @Column(nullable = false)
  private LocalDateTime expiredAt;

  @Column
  private LocalDateTime cancelledAt;

  @Column(nullable = false)
  private String subscribeId;

  public void cancel(LocalDateTime now) {
    this.status = SubscriptionStatus.PENDING_CANCELLATION;
    this.cancelledAt = now;
  }
}
