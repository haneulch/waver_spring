package com.mybury.waver.domain;

import com.mybury.waver.common.code.BadgeStep;
import com.mybury.waver.common.code.YesNo;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  @Transient
  private BadgeStep badgeStep;

  @Builder.Default
  @Enumerated(value = EnumType.STRING)
  @Column(length = 1, nullable = false)
  private YesNo selectYn = YesNo.N;

  @Setter
  @Builder.Default
  @Enumerated(value = EnumType.STRING)
  @Column(length = 1, nullable = false)
  private YesNo achieveYn = YesNo.N;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "badgeTypeId", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private BadgeType badgeType;

  public static Badge createDefaultBadgeFor(User user) {
    return Badge.builder()
        .achieveCount(30)
        .achieveYn(YesNo.Y)
        .selectYn(YesNo.Y)
        .userId(user.getId())
        .badgeTypeId(1L)
        .build();
  }

  public void incrementAchieveCount() {
    this.achieveCount++;
  }

  public BadgeStep getBadgeStep() {
    return BadgeStep.getStep(this.achieveCount);
  }
}
