package com.mybury.waver.web.message.v1.follow;

import java.util.List;

public record GetFollowersResponse(
    List<FollowElement> followingUsers,
    List<FollowElement> followerUsers
) {

}


