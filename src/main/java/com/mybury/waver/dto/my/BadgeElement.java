package com.mybury.waver.dto.my;

import com.mybury.waver.common.code.BadgeStep;

public record BadgeElement(
    int id,
    String name,
    BadgeStep step
) {
}
