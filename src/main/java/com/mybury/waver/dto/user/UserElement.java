package com.mybury.waver.dto.user;

import com.mybury.waver.common.code.BadgeStep;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Badge;
import com.mybury.waver.domain.User;

public record UserElement(
    long id,
    String name,
    String imgUrl,
    String badgeTitle,
    String badgeImgUrl
) {
  public static UserElement of(User user) {
    Badge selected = user.getBadges().stream().filter(item -> item.getSelectYn() == YesNo.Y).findFirst().orElse(null);
    if (selected == null) {
      return new UserElement(user.getId(), user.getName(), user.getImgUrl(), null, null);
    }
    BadgeStep step = BadgeStep.getStep(selected.getAchieveCount());
    String badgeImgUrl = BadgeStep.getImgUrl(step, selected.getBadgeType());
    return new UserElement(user.getId(), user.getName(), user.getImgUrl(), selected.getBadgeType().getTitle(), badgeImgUrl);
  }
}
