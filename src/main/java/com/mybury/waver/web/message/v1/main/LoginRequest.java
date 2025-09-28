package com.mybury.waver.web.message.v1.main;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @Email
    @NotBlank
    @Schema(description = "이메일")
    String email,

    @NotBlank
    String uid
) {

}
