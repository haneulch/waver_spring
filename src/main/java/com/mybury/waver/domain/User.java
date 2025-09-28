package com.mybury.waver.domain;

import com.mybury.waver.common.code.AccountType;
import com.mybury.waver.common.code.PremiumStatus;
import com.mybury.waver.common.code.UserStatus;
import com.mybury.waver.common.code.YesNo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
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