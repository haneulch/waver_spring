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
 * mybury 레거시 DB의 사용자 테이블 (mybury.mt_user).
 * waver(bucket DB)와 같은 MySQL 서버의 별도 database라 catalog로 cross-database 접근한다.
 * 이관/조회 전용 — 읽기 전용 매핑(@Immutable)이며 waver에서 이 테이블에 쓰지 않는다.
 */
@Getter
@Entity
@Immutable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mt_user", catalog = "mybury")
public class MyburyUser {

  @Id
  private String id;

  @ColumnTransformer(read = MyburyEncryption.DEC_USER_EMAIL)
  private String email;

  @Column(name = "account_type")
  private Integer accountType;

  @ColumnTransformer(read = MyburyEncryption.DEC_USER_NAME)
  private String name;

  @Column(name = "img_url")
  @ColumnTransformer(read = MyburyEncryption.DEC_USER_IMG_URL)
  private String imgUrl;

  @Column(name = "user_seq")
  private Integer userSeq;
}
