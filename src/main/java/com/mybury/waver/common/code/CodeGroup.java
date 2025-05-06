package com.mybury.waver.common.code;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Getter
@AllArgsConstructor
public enum CodeGroup {
  REPORT(ReportReason.class);

  private final Class<? extends CodeEnum> clazz;

  private static final Map<String, CodeGroup> map =
      Arrays.stream(values()).collect(toMap(Enum::name, Function.identity()));

  public static CodeGroup get(String name) {
    return map.get(name);
  }
}
