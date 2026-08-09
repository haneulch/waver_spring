package com.mybury.waver.scheduler;

import com.mybury.waver.common.code.MigrationStatus;
import com.mybury.waver.domain.MigrationInfo;
import com.mybury.waver.repository.MigrationInfoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * mybury 데이터 이관 스케줄러
 * 이동요청 API로 인서트된 MigrationInfo(REQUESTED) 건만 조회해 이관 처리한다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MyburyMigrationScheduler {

  private final MigrationInfoRepository migrationInfoRepository;

  // 매일 04:00 (트래픽 적은 시간대). 주기는 이관 로직 구현 시 조정
  @Scheduled(cron = "0 0 4 * * *")
  @Transactional
  public void migrate() {
    List<MigrationInfo> targets = migrationInfoRepository.findByStatus(MigrationStatus.REQUESTED);
    if (targets.isEmpty()) {
      return;
    }
    log.info("mybury migration start. target count: {}", targets.size());

    for (MigrationInfo info : targets) {
      // TODO: mybury DB 연동 후 데이터 이관 구현
      //  1. userId로 waver 사용자 조회, 이메일 기준 mybury DB에서 기존 데이터 조회 (버킷리스트 등)
      //  2. waver 테이블로 변환/저장
      //  3. 성공 시 info.setStatus(MigrationStatus.COMPLETED)
      //  4. 실패 시 REQUESTED 유지해서 다음 주기에 재시도, 실패 사유 로깅
      log.info("mybury migration target: userId={}", info.getUserId());
    }
  }
}
