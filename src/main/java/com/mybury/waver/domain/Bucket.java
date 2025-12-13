package com.mybury.waver.domain;

import com.mybury.waver.common.code.BucketStatus;
import com.mybury.waver.common.code.ContentType;
import com.mybury.waver.common.code.ExposureStatus;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Bucket extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  private String memo;

  private Long userId;

  private Long categoryId;

  @Builder.Default
  @Enumerated(value = EnumType.STRING)
  @Column(length = 10, nullable = false)
  private ContentType type = ContentType.ORIGINAL;

  @Builder.Default
  @Enumerated(value = EnumType.STRING)
  @Column(length = 10, nullable = false)
  private ExposureStatus exposureStatus = ExposureStatus.PRIVATE;

  @Builder.Default
  @Enumerated(value = EnumType.STRING)
  @Column(length = 1, nullable = false)
  private YesNo pin = YesNo.N;

  @Builder.Default
  @Enumerated(value = EnumType.STRING)
  @Column(length = 10, nullable = false)
  private BucketStatus status = BucketStatus.PROGRESS;

  @Builder.Default
  @Enumerated(value = EnumType.STRING)
  @Column(length = 1, nullable = false)
  private YesNo scrapYn = YesNo.N;

  private LocalDate targetDate;

  @Transient
  private Integer dDay;

  @Builder.Default
  @Column(nullable = false)
  private Integer userCount = 0;

  @Builder.Default
  @Column(nullable = false)
  private Integer goalCount = 0;

  private LocalDateTime completedDate;

  @Builder.Default
  @Column(nullable = false)
  private Integer seq = 0;

  private String imgUrl;

  @Builder.Default
  @Column(nullable = false)
  private Integer likeCount = 0;

  private String keywords;

  @Builder.Default
  @Enumerated(value = EnumType.STRING)
  @Column(length = 1, nullable = false)
  private YesNo deleted = YesNo.N;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "categoryId", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Category category;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "bucket")
  private List<Comment> comments;

  @PostLoad
  private void postLoad() {
    if (this.targetDate != null) {
      this.dDay = (int) ChronoUnit.DAYS.between(LocalDate.now(), targetDate);
    }
  }

  public static Bucket copy(Bucket bucket, long userId, long categoryId) {
    return Bucket.builder()
        .title(bucket.getTitle())
        .memo(bucket.getMemo())
        .userId(userId)
        .categoryId(categoryId)
        .type(bucket.getType())
        .targetDate(bucket.getTargetDate())
        .dDay(bucket.getDDay())
        .goalCount(bucket.getGoalCount())
        .imgUrl(bucket.getImgUrl())
        .keywords(bucket.getKeywords())
        .build();
  }
}
