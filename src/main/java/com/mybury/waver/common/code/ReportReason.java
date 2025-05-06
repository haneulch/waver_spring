package com.mybury.waver.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportReason implements CodeEnum {
  ADVERTISEMENT("광고/홍보성"),
  ABUSE("욕설/인신 공격"),
  ILLEGAL_INFO("불법정보"),
  SEXUAL_CONTENT("음란성/선정성"),
  PERSONAL_INFO("개인정보 노출"),
  SPAM("같은 내용 도배"),
  POLITICAL("정치적/사회적 의견"),
  ETC("기타");

  private final String content;
}
