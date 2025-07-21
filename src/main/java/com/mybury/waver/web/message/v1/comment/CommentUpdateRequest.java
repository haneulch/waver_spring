package com.mybury.waver.web.message.v1.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CommentUpdateRequest(
    @NotBlank
    @Schema(description = "메세지")
    String content
) {

}
