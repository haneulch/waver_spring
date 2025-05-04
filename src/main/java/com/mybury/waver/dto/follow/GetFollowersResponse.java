package com.mybury.waver.dto.follow;

import java.util.List;

public record GetFollowersResponse(
    List<FollowElement> followingUsers,
    List<FollowElement> followerUsers
) {
}


