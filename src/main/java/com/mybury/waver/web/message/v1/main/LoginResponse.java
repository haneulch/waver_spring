package com.mybury.waver.web.message.v1.main;

import com.mybury.waver.common.code.PremiumStatus;

public record LoginResponse(
    String accessToken,
    PremiumStatus status
) {

}
