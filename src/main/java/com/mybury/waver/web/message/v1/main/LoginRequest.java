package com.mybury.waver.web.message.v1.main;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank
    String uid
) {

}
