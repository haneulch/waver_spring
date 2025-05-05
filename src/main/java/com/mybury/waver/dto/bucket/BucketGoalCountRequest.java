package com.mybury.waver.dto.bucket;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BucketGoalCountRequest(
    @Min(1)
    @NotNull
    @Schema(description = "목표 횟수")
    Integer goalCount
) {
}
