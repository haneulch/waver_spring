package com.mybury.waver.domain;

import com.mybury.waver.common.code.YesNo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Badge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(nullable = false)
    private Integer achieveCount = 0;

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    @Column(length = 1, nullable = false)
    private YesNo selectYn = YesNo.N;

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    @Column(length = 1, nullable = false)
    private YesNo achieveYn = YesNo.N;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_type_id")
    private BadgeType badgeType;

    public static Badge createDefaultBadgeFor(User user) {
        return Badge.builder()
            .achieveYn(YesNo.Y)
            .selectYn(YesNo.Y)
            .user(user)
            .badgeType(BadgeType.builder().id(1L).build())
            .build();
    }
}
