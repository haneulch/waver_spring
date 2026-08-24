package com.mybury.waver.scheduler;

import com.mybury.waver.common.code.MigrationStatus;
import com.mybury.waver.domain.MigrationInfo;
import com.mybury.waver.repository.MigrationInfoRepository;
import com.mybury.waver.service.MyburyMigrationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * mybury 데이터 이관 스케줄러
 * 이동요청 API로 인서트된 MigrationInfo(REQUESTED) 건만 조회해 이관 처리한다.
 * 사용자 단위 트랜잭션 — 한 명 실패해도 나머지는 계속 진행하고, 실패 건은 REQUESTED로 남아 다음 주기에 재시도된다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MyburyMigrationScheduler {

  private final MigrationInfoRepository migrationInfoRepository;
  private final MyburyMigrationService myburyMigrationService;

  // 매일 04:00 (트래픽 적은 시간대). 로컬 검증용으로 프로퍼티 오버라이드 가능
  @Scheduled(cron = "${waver.mybury.migration-cron:0 0 4 * * *}")
  public void migrate() {
    List<MigrationInfo> targets = migrationInfoRepository.findByStatus(MigrationStatus.REQUESTED);
    if (targets.isEmpty()) {
      return;
    }
    log.info("mybury migration start. target count: {}", targets.size());

    for (MigrationInfo info : targets) {
      try {
        myburyMigrationService.migrateUser(info.getId());
      } catch (Exception e) {
        log.error("mybury migration failed. userId={} — will retry next run", info.getUserId(), e);
      }
    }
  }
}
