package com.mybury.waver.dto.follow;

import com.mybury.waver.domain.Follow;
import com.mybury.waver.domain.User;

public record FollowElement(
    long id,
    String name,
    String imgUrl,
    boolean mutualFollow
) {
  public static FollowElement follow(Follow follow, boolean mutualFollow) {
    User followUser = follow.getFollowUser();
    return new FollowElement(follow.getFollowUserId(), followUser.getName(), followUser.getImgUrl(), mutualFollow);
  }

  public static FollowElement follower(Follow follow, boolean mutualFollow) {
    User follower = follow.getUser();
    return new FollowElement(follow.getUserId(), follower.getName(), follower.getImgUrl(), mutualFollow);
  }
}
