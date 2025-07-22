package com.mybury.waver.web.message.v1.follow;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record FollowRequest(
    @NotNull
    @Schema(description = "팔로우할 회원 ID")
    Long followUserId
) {

}
