package com.mybury.waver.dto.explore;


import com.mybury.waver.common.code.FixedKeyword;

import java.util.Arrays;
import java.util.List;

public record KeywordResponse(
    String code,
    String name
) {
  public static List<KeywordResponse> getAllKeywords() {
    return Arrays.stream(FixedKeyword.values()).map(
        value -> new KeywordResponse(value.getCode(), value.getName())
    ).toList();
  }
}
