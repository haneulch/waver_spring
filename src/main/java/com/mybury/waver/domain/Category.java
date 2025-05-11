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
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static com.mybury.waver.common.Constants.DEFAULT_CATEGORY;

@Setter
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Category extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private Long userId;

  private int seq;

  @Builder.Default
  @Enumerated(value = EnumType.STRING)
  @Column(length = 1, nullable = false)
  private YesNo deleted = YesNo.N;

  @Builder.Default
  @Enumerated(value = EnumType.STRING)
  @Column(length = 1, nullable = false)
  private YesNo defaultYn = YesNo.N;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", insertable = false, updatable = false)
  private User user;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
  private List<Bucket> buckets;

  public static Category createDefaultCategoryFor(long userId) {
    return Category.builder().name(DEFAULT_CATEGORY).defaultYn(YesNo.Y).userId(userId).build();
  }
}
