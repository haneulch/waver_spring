package com.mybury.waver.dto.user;

import com.mybury.waver.common.code.PremiumStatus;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Badge;
import com.mybury.waver.domain.User;

public record ProfileResponse(
    String email,
    String name,
    String imgUrl,
    String bio,
    String badgeTitle,
    String badgeImgUrl,
    PremiumStatus premiumStatus,
    YesNo followYn,
    Long followingCount,
    Long followerCount
) {
  public ProfileResponse(User user, Badge badge, YesNo followYn, long followingCount, long followerCount) {
    this(user.getEmail(), user.getName(), user.getImgUrl(), user.getBio(), badge.getBadgeType().getTitle(), badge.getBadgeType().getImgUrl1(), user.getPremiumStatus(), followYn, followingCount, followerCount);
  }
}
