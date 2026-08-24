package com.mybury.waver.web.message.v1.user;

import jakarta.validation.constraints.NotBlank;

public record FcmTokenRequest(
    @NotBlank
    String fcmToken
) {
}
