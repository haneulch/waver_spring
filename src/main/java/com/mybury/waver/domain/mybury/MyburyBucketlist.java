package com.mybury.waver.domain.mybury;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Immutable;

/**
 * mybury 레거시 DB의 버킷리스트 테이블 (mybury.mt_bucketlist). 이관 전용 읽기 전용 매핑.
 * title/memo/img_url_*은 레거시와 동일하게 SQL 레벨 복호화 (CHAR(1250) 캐스팅도 레거시 규격 유지).
 */
@Getter
@Entity
@Immutable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mt_bucketlist", catalog = "mybury")
public class MyburyBucketlist {

  @Id
  private String id;

  @ColumnTransformer(read = MyburyEncryption.DEC_BUCKETLIST_TITLE)
  private String title;

  @ColumnTransformer(read = MyburyEncryption.DEC_BUCKETLIST_MEMO)
  private String memo;

  @Column(name = "img_url_1")
  @ColumnTransformer(read = MyburyEncryption.DEC_BUCKETLIST_IMG_URL_1)
  private String imgUrl1;

  @Column(name = "img_url_2")
  @ColumnTransformer(read = MyburyEncryption.DEC_BUCKETLIST_IMG_URL_2)
  private String imgUrl2;

  @Column(name = "img_url_3")
  @ColumnTransformer(read = MyburyEncryption.DEC_BUCKETLIST_IMG_URL_3)
  private String imgUrl3;

  @Column(name = "d_date")
  private LocalDateTime dDate;

  @Column(name = "goal_count")
  private Integer goalCount;

  @Column(name = "user_count")
  private Integer userCount;

  private Boolean pin;

  @Column(name = "category_id")
  private String categoryId;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "completed_dt")
  private LocalDateTime completedDt;

  @Column(name = "order_seq")
  private Integer orderSeq;
}
