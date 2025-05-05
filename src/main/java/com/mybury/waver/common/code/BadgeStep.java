package com.mybury.waver.common.code;

import com.mybury.waver.domain.BadgeType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BadgeStep {
  STEP0(0, 0),
  STEP1(1, 9),
  STEP2(10, 29),
  STEP3(30, Integer.MAX_VALUE);

  private final int min;
  private final int max;

  public static BadgeStep getStep(int count) {
    for (BadgeStep step : values()) {
      if (count >= step.min && count <= step.max) {
        return step;
      }
    }
    return STEP0;
  }

  public static String getImgUrl(BadgeStep step, BadgeType badgeType) {
    return switch (step) {
      case STEP0 -> badgeType.getImgUrl1();
      case STEP1 -> badgeType.getImgUrl2();
      case STEP2 -> badgeType.getImgUrl3();
      case STEP3 -> badgeType.getImgUrl4();
    };
  }
}
