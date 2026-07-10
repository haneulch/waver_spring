package com.mybury.waver.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class FreeTier extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column
  private long userId;

  @Builder.Default
  @Column
  private int imageLimit = 0;

  @Builder.Default
  @Column
  private int togetherLimit = 0;

  @Builder.Default
  @Column
  private int imageUsed = 0;

  @Builder.Default
  @Column
  private int togetherUsed = 0;

  public static FreeTier createDefaultFreeTier(long userId) {
    return FreeTier.builder().userId(userId).imageLimit(1).togetherLimit(3).build();
  }

  public boolean canUseMultiImage() {
    return imageUsed < imageLimit;
  }

  public void useMultiImage() {
    imageUsed++;
  }

  public boolean canUseTogether() {
    return togetherUsed < togetherLimit;
  }

  public void useTogether() {
    togetherUsed++;
  }
}
