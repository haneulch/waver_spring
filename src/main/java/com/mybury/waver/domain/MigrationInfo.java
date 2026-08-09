package com.mybury.waver.domain;

import com.mybury.waver.common.code.MigrationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * mybury 데이터 이관 요청 정보
 * 이동요청 API 호출 시 REQUESTED로 인서트되고, 스케줄러가 이 테이블의 REQUESTED 건만 조회해 이관 처리한다.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MigrationInfo extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private Long userId;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(length = 10, nullable = false)
  private MigrationStatus status = MigrationStatus.REQUESTED;

  public static MigrationInfo request(Long userId) {
    return MigrationInfo.builder()
        .userId(userId)
        .build();
  }
}
