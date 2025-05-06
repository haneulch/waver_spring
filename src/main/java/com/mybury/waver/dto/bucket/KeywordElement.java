package com.mybury.waver.dto.bucket;

import com.mybury.waver.common.code.FixedKeyword;

public record KeywordElement(
    String code,
    String name
) {
  public KeywordElement(FixedKeyword keyword) {
    this(keyword.getCode(), keyword.getName());
  }
}
