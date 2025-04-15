package com.mybury.waver.domain;

import static com.mybury.waver.common.Constants.DEFAULT_CATEGORY;
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
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Category extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Enumerated(value = EnumType.STRING)
  @Column(columnDefinition = "varchar(1) default 'N'")
  private YesNo defaultYn;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  public static Category createDefaultCategoryFor(User user) {
    return Category.builder().name(DEFAULT_CATEGORY).defaultYn(YesNo.Y).user(user).build();
  }
}
