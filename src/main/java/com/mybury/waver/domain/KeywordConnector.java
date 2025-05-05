package com.mybury.waver.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class KeywordConnector {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long bucketId;

  private Integer keywordId;

  private Long userId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bucketId", insertable = false, updatable = false)
  private Bucket bucket;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "keywordId", insertable = false, updatable = false)
  private Keyword keyword;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", insertable = false, updatable = false)
  private User user;
}
