package com.mybury.waver.domain;

import com.mybury.waver.common.code.BucketStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 함께하기(TOGETHER) 버킷 참여자.
 * 소유자 포함 참여자 전원이 각자 row를 가지며, 진행도(userCount/status)를 개인별로 관리한다.
 * categoryId는 참여자 본인의 카테고리 — 소유자는 버킷 생성 시 지정한 카테고리, 친구는 자신의 기본(default) 카테고리.
 */
@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uk_bucket_member", columnNames = {"bucketId", "userId"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class BucketMember extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long bucketId;

  @Column(nullable = false)
  private Long userId;

  private Long categoryId;

  @Builder.Default
  @Column(nullable = false)
  private Integer userCount = 0;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(length = 10, nullable = false)
  private BucketStatus status = BucketStatus.PROGRESS;

  private LocalDateTime completedDate;

  public static BucketMember of(Long bucketId, Long userId, Long categoryId) {
    return BucketMember.builder()
        .bucketId(bucketId)
        .userId(userId)
        .categoryId(categoryId)
        .build();
  }
}
