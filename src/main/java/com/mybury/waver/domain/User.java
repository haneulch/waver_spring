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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) default 'ANDROID'")
    private AccountType accountType;

    @Column(nullable = false)
    private String name;

    @Setter
    private String imgUrl;

    private String bio;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(1) default 'N'")
    private YesNo alarmYn;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) default 'ACTIVE'")
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) default 'NONE'")
    private PremiumStatus premiumStatus;

    private LocalDateTime lastLoginAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Bucket> bucketlist;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<RecentSearch> recentSearch;

}