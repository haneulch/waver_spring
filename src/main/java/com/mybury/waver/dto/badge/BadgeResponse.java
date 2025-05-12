package com.mybury.waver.dto.badge;

import com.mybury.waver.common.code.BadgeStep;
import com.mybury.waver.domain.Badge;
import com.mybury.waver.domain.BadgeType;
import com.mybury.waver.util.FileImageUtils;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record BadgeResponse(
    @Schema(description = "배지명")
    String title,

    @Schema(description = "배지 이미지")
    String imgUrl,

    @Schema(description = "배지 단계")
    BadgeStep step
) {
  public static List<BadgeResponse> of(List<BadgeType> badgeTypes, List<Badge> badges) {
    return badgeTypes.stream().map(type -> {
      Badge badge = badges.stream().filter(item -> item.getBadgeTypeId().equals(type.getId())).findFirst().orElse(null);
      if (badge != null) {
        BadgeStep step = BadgeStep.getStep(badge.getAchieveCount());
        String imgUrl = BadgeStep.getImgUrl(step, type);
        return new BadgeResponse(type.getTitle(), imgUrl, step);
      }
      return new BadgeResponse(type.getTitle(), FileImageUtils.staticPath(type.getImgUrl1()), BadgeStep.STEP0);
    }).toList();
  }
}
