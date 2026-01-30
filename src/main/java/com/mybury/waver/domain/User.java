package com.mybury.waver.domain;

import com.mybury.waver.common.code.AccountType;
import com.mybury.waver.common.code.PremiumStatus;
import com.mybury.waver.common.code.UserStatus;
import com.mybury.waver.common.code.YesNo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, length = 1500)
  private String uid;

  @Column(nullable = false)
  private String email;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(length = 10, nullable = false)
  private AccountType accountType = AccountType.ANDROID;

  @Column(nullable = false)
  private String name;

  @Setter
  private String imgUrl;

  private String bio;

  private Locale locale;

  private String fcmToken;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(length = 1, nullable = false)
  private YesNo alarmYn = YesNo.N;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(length = 1, nullable = false)
  private YesNo deleteYn = YesNo.N;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(length = 10, nullable = false)
  private UserStatus status = UserStatus.ACTIVE;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(length = 10, nullable = false)
  private PremiumStatus premiumStatus = PremiumStatus.NONE;

  private LocalDateTime lastLoginAt;

  private LocalDateTime withdrawnAt;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
  private List<Bucket> bucketlist;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
  private List<RecentSearch> recentSearch;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
  private List<Badge> badges;

  public Locale getLocale() {
    return Optional.ofNullable(locale).orElse(Locale.getDefault());
  }
}