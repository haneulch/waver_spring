package com.mybury.waver.dto.user;

import com.mybury.waver.common.code.PremiumStatus;

public record WaverPlusResponse(
    PremiumStatus premiumStatus
) {
}
