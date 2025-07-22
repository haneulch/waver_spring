package com.mybury.waver.web.message.v1.bucket;

import com.mybury.waver.common.code.BucketStatus;
import com.mybury.waver.common.code.SortType;
import com.mybury.waver.common.code.YesNo;
import io.swagger.v3.oas.annotations.media.Schema;

public record BucketRequest(
    @Schema(description = "d-day 버킷만 조회시 Y")
    YesNo dDayBucketOnly,

    @Schema(description = "d-day 지난 버킷 조회시 Y")
    YesNo isPassed,

    @Schema(implementation = BucketStatus.class)
    BucketStatus status,

    @Schema(implementation = SortType.class)
    SortType sort,

    @Schema(description = "검색어")
    String query,

    @Schema(description = "카테고리 ID")
    Long categoryId
) {

}
