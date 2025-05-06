package com.mybury.waver.dto.my;

import io.swagger.v3.oas.annotations.media.Schema;

public record MyWaveInfoResponse(
    @Schema(description = "뱃지획득")
    int totalBadgeCount,

    @Schema(description = "좋아요")
    int totalLikeCount,

    @Schema(description = "버킷리스트")
    int totalBucketCount,

    @Schema(description = "현재 배지 이미지")
    String badgeImgUrl
) {
}
