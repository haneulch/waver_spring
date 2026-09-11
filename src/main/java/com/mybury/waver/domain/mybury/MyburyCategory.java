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

  // 레거시는 char(1). columnDefinition을 명시해야 schema validate를 통과한다
  @Column(name = "is_default", columnDefinition = "char(1)")
  private String isDefault;

  public boolean isDefaultCategory() {
    return "Y".equals(isDefault);
  }
}
