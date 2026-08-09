package com.mybury.waver.repository;

import com.mybury.waver.common.code.MigrationStatus;
import com.mybury.waver.domain.MigrationInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MigrationInfoRepository extends JpaRepository<MigrationInfo, Long> {

  Optional<MigrationInfo> findByUserId(Long userId);

  List<MigrationInfo> findByStatus(MigrationStatus status);
}
