package com.mybury.waver.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CommentCreateRequest(
    @Min(1)
    @NotNull
    Long bucketId,

    @NotBlank
    @Schema(description = "메세지")
    String content,

    @Schema(description = "멘션된 회원 ID")
    List<String> mentionIds
) {

}
