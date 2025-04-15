package com.mybury.waver.domain;

import com.mybury.waver.common.code.BucketStatus;
import com.mybury.waver.common.code.ContentType;
import com.mybury.waver.common.code.ExposureStatus;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

  @Enumerated(value = EnumType.STRING)
  @Column(columnDefinition = "varchar(10) default 'ORIGINAL'")
  private ContentType type;

  @Enumerated(value = EnumType.STRING)
  @Column(columnDefinition = "varchar(10) default 'PRIVATE'")
  private ExposureStatus exposureStatus;

  @Enumerated(value = EnumType.STRING)
  @Column(columnDefinition = "varchar(1) default 'N'")
  private YesNo pin;

  @Enumerated(value = EnumType.STRING)
  @Column(columnDefinition = "varchar(10) default 'PROGRESS'")
  private BucketStatus status;

  @Enumerated(value = EnumType.STRING)
  @Column(columnDefinition = "varchar(1) default 'N'")
  private YesNo scrapYn;

  private LocalDate targetDate;

  @Column(columnDefinition = "int default 0")
  private int userCount;

  @Column(columnDefinition = "int default 0")
  private int goalCount;

  private LocalDateTime completedDate;

  @Column(columnDefinition = "int default 0")
  private int seq;

  private String imgUrl1;

  private String imgUrl2;

  private String imgUrl3;

  @Column(columnDefinition = "int default 0")
  private int likeCount;

  @Enumerated(value = EnumType.STRING)
  @Column(columnDefinition = "varchar(1) default 'N'")
  private YesNo deleted;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "bucket")
  private List<Comment> comments;
}
