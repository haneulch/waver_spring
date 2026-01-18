package com.mybury.waver.web.message.v1.user;

import com.mybury.waver.common.code.UserStatus;

import java.time.LocalDateTime;

public record UserStatusResponse(
  UserStatus status,
  LocalDateTime lastLoginAt,
  LocalDateTime withdrawnAt
) {
}
