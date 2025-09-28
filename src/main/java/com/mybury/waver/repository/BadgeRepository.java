package com.mybury.waver.repository;

import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Badge;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BadgeRepository extends JpaRepository<Badge, Long> {

  Optional<Badge> findByUserIdAndSelectYn(Long userId, YesNo selectYn);

  List<Badge> findByUserId(Long userId);

  int countByUserId(Long userId);

  List<Badge> findByUserIdAndBadgeType_CodeIn(Long userId, List<String> badgeTypeCodes);

  boolean existsByUserIdAndAchieveCountGreaterThanAndId(long userId, int count, long id);

  @Modifying
  @Transactional
  @Query("UPDATE Badge b SET b.selectYn = 'N' WHERE b.userId = :userId")
  void unselectAllBadge(long userId);

  @Modifying
  @Transactional
  @Query("UPDATE Badge b SET b.selectYn = :selectYn WHERE b.id = :badgeId")
  void updateSelectYn(long badgeId, YesNo selectYn);
}
