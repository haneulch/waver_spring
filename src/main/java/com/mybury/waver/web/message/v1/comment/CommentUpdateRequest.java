package com.mybury.waver.web.message.v1.comment;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CommentUpdateRequest(
    @NotBlank
    @Schema(description = "메세지")
    String content,

    @Schema(description = "멘션된 회원 ID")
    List<String> mentionIds
) {

}
