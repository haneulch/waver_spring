package com.mybury.waver.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;

public record CommentCreateResponse(
    @Schema(description = "test")
    String test
) {

}
