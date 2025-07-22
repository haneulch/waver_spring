package com.mybury.waver.web.message.v1.user;

import com.mybury.waver.common.code.PremiumStatus;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Badge;
import com.mybury.waver.domain.User;
import com.mybury.waver.util.FileImageUtils;

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
        this(user.getEmail(), user.getName(), FileImageUtils.imagePath(user.getImgUrl()), user.getBio(),
            badge.getBadgeType().getTitle(), FileImageUtils.staticPath(badge.getBadgeType().getImgUrl1()),
            user.getPremiumStatus(), followYn, followingCount, followerCount);
    }
}
