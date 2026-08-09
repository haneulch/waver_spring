package com.mybury.waver.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * mybury 데이터 이관 상태
 * REQUESTED: 이관 요청됨(스케줄러 처리 대상), COMPLETED: 이관 완료
 */
@Getter
@AllArgsConstructor
public enum MigrationStatus {
  REQUESTED("REQUESTED"), COMPLETED("COMPLETED");
  private final String value;
}
