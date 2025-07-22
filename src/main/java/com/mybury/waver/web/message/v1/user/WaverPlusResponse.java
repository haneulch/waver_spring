package com.mybury.waver.web.message.v1.user;

import com.mybury.waver.common.code.PremiumStatus;

public record WaverPlusResponse(
    PremiumStatus premiumStatus
) {

}
