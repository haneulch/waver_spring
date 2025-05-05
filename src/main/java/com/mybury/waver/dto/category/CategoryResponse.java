package com.mybury.waver.dto.category;

import com.mybury.waver.common.code.YesNo;

public record CategoryResponse(
    Long id,
    String name,
    YesNo defaultYn,
    int bucketlistCount
) {
}
