package com.mybury.waver.dto.follow;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record FollowRequest(
    @NotNull
    @Schema(name = "팔로우할 회원 ID")
    Long followUserId
) {
}
