package com.mybury.waver.web.message.v1.my;

import com.mybury.waver.common.code.BadgeStep;

public record BadgeElement(
    int id,
    String name,
    BadgeStep step
) {

}
