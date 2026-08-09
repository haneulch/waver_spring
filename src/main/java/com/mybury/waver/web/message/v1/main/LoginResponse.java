package com.mybury.waver.web.message.v1.main;

import com.mybury.waver.common.code.PremiumStatus;
import com.mybury.waver.common.code.YesNo;

public record LoginResponse(
    String accessToken,
    PremiumStatus status,
    YesNo myburyYn
) {

}
