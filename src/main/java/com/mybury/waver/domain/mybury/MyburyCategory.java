package com.mybury.waver.domain.mybury;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Immutable;

/**
 * mybury 레거시 DB의 카테고리 테이블 (mybury.mt_category). 이관 전용 읽기 전용 매핑.
 */
@Getter
@Entity
@Immutable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mt_category", catalog = "mybury")
public class MyburyCategory {

  @Id
  private String id;

  @ColumnTransformer(read = MyburyEncryption.DEC_CATEGORY_NAME)
  private String name;

  private Integer priority;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "is_default", length = 1)
  private String isDefault;

  public boolean isDefaultCategory() {
    return "Y".equals(isDefault);
  }
}
