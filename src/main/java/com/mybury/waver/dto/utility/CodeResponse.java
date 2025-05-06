package com.mybury.waver.dto.utility;

import com.mybury.waver.common.code.CodeEnum;

public record CodeResponse(
    String code,
    String content
) {
  public CodeResponse(CodeEnum codeEnum) {
    this(codeEnum.name(), codeEnum.getContent());
  }
}
