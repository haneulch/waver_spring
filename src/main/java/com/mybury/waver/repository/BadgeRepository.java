package com.mybury.waver.repository;

import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Badge;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {

    Optional<Badge> findByUserIdAndSelectYn(Long userId, YesNo selectYn);

    List<Badge> findByUserId(Long userId);

    int countByUserId(Long userId);

    List<Badge> findByUserIdAndBadgeType_CodeIn(Long userId, List<String> badgeTypeCodes);
}
