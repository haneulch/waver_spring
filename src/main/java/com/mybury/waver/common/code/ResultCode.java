package com.mybury.waver.common.code;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
  SUCCESS("2000", "SUCCESS"),
  BAD_REQUEST("4000", "BAD_REQUEST"),
  VALIDATION_FAILED("4001", "VALIDATION_FAILED"),
  UNAUTHORIZED("4010", "UNAUTHORIZED"),
  FORBIDDEN("4030", "FORBIDDEN"),
  NOT_FOUND("4040", "NOT_FOUND"),
  INTERNAL_SERVER_ERROR("5000", "INTERNAL_SERVER_ERROR"),
  TOKEN_EXPIRED("5001", "TOKEN_EXPIRED"),
  INVALID_TOKEN("5002", "INVALID_TOKEN"),

  CATEGORY_CANNOT_DUPLICATE("6000", "CATEGORY_CANNOT_DUPLICATE"),
  EMAIL_OR_NAME_CANNOT_DUPLICATE("6001", "EMAIL_OR_NAME_CANNOT_DUPLICATE"),

  // 선택할 수 없는 뱃지 오류
  BADGE_CANNOT_SELECT("7000", "BADGE_CANNOT_SELECT"),

  // 저장된 관심 키워드 없음 오류
  KEYWORD_NOT_FOUND("8000", "KEYWORD_NOT_FOUND"),

  // 이미지 저장 제한 초과 (무료: 기본 1개 + 3개 저장 1회 / 구독: 최대 3개)
  IMAGE_LIMIT_EXCEEDED("8100", "IMAGE_LIMIT_EXCEEDED"),

  // 함께하기 사용 제한 초과 (무료: 최대 3회)
  TOGETHER_LIMIT_EXCEEDED("8101", "TOGETHER_LIMIT_EXCEEDED"),

  // mybury 회원이 아닌 사용자의 이관 요청 (myburyYn != Y)
  MIGRATION_NOT_ALLOWED("8200", "MIGRATION_NOT_ALLOWED"),

  // 이미 이관 완료된 사용자의 이관 요청
  MIGRATION_ALREADY_COMPLETED("8201", "MIGRATION_ALREADY_COMPLETED"),

  // 이미 이관 요청한 사용자의 중복 요청
  MIGRATION_ALREADY_REQUESTED("8202", "MIGRATION_ALREADY_REQUESTED"),

  // 탈퇴한 사용자
  WITHDRAWAL_USER("9000", "WITHDRAWAL_USER"),

  UNKNOWN("9999", "UNKNOWN"),
  ;

  private static final Map<String, ResultCode> resultCodes = Arrays.stream(values())
      .collect(Collectors.toMap(ResultCode::getCode, Function.identity()));

  public static ResultCode get(String code) {
    return resultCodes.getOrDefault(code, UNKNOWN);
  }

  private final String code;
  private final String description;
}
