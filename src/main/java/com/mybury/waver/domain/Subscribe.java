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

  /** 자동 갱신됨 — 활성 상태로 만료일을 연장하고 취소 표시를 해제한다. */
  public void renew(LocalDateTime expiredAt) {
    this.status = SubscriptionStatus.ACTIVE;
    if (expiredAt != null) {
      this.expiredAt = expiredAt;
    }
    this.cancelledAt = null;
  }

  /** 자동 갱신 해제됨 — 만료일까지 접근은 유지되므로 status는 ACTIVE로 두고 취소 시각만 기록한다. */
  public void markCancelled(LocalDateTime cancelledAt, LocalDateTime expiredAt) {
    this.status = SubscriptionStatus.ACTIVE;
    if (this.cancelledAt == null) {
      this.cancelledAt = cancelledAt;
    }
    if (expiredAt != null) {
      this.expiredAt = expiredAt;
    }
  }

  /** 만료·보류 등으로 접근이 종료됨. */
  public void expire() {
    this.status = SubscriptionStatus.EXPIRED;
  }
}
