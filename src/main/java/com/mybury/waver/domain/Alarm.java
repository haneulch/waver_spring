package com.mybury.waver.domain;

import com.mybury.waver.common.code.PushType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Alarm extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;

  @Enumerated(EnumType.STRING)
  private PushType type;

  private String message;

  // 알림을 유발한 상대의 프로필 이미지(팔로우 알림에서 팔로워 사진). 그 외 타입은 null.
  private String imgUrl;

  // 버킷 관련 알림(좋아요/댓글/디데이/함께하기)에서 대상 버킷 ID. 그 외 타입은 null.
  private Long bucketId;

  private boolean isRead;
}
