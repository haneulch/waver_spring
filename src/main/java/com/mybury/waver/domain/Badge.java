package com.mybury.waver.domain;

import com.mybury.waver.common.code.YesNo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Badge extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;

  private Long badgeTypeId;

  @Builder.Default
  @Column(nullable = false)
  private Integer achieveCount = 0;

  @Builder.Default
  @Enumerated(value = EnumType.STRING)
  @Column(length = 1, nullable = false)
  private YesNo selectYn = YesNo.N;

  @Builder.Default
  @Enumerated(value = EnumType.STRING)
  @Column(length = 1, nullable = false)
  private YesNo achieveYn = YesNo.N;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", insertable = false, updatable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "badgeTypeId", insertable = false, updatable = false)
  private BadgeType badgeType;

  public static Badge createDefaultBadgeFor(User user) {
    return Badge.builder()
        .achieveYn(YesNo.Y)
        .selectYn(YesNo.Y)
        .userId(user.getId())
        .badgeTypeId(1L)
        .build();
  }
}
