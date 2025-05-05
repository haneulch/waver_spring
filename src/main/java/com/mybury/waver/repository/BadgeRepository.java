package com.mybury.waver.repository;

import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
  Optional<Badge> findByUserIdAndSelectYn(Long userId, YesNo selectYn);

  List<Badge> findByUserId(Long userId);
}
