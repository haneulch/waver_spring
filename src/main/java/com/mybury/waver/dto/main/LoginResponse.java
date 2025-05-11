package com.mybury.waver.dto.main;

import com.mybury.waver.common.code.PremiumStatus;

public record LoginResponse(
    String accessToken,
    PremiumStatus status
) {

}
