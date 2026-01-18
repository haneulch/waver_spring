package com.mybury.waver.web.message.v1.user;

public record UserStatusRequest(
  String email,
  String uid
) {
}
