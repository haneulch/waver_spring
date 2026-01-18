package com.mybury.waver.repository;

import com.mybury.waver.domain.BadgeType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeTypeRepository extends JpaRepository<BadgeType, Long> {

  List<BadgeType> findByCodeIn(List<String> codes);
}
