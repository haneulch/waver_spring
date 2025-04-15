package com.mybury.waver.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CommentCreateRequest(
    @NotBlank
    @Schema(description = "name")
    String name,
    @Schema(description = "메세지")
    String message
) {

}
