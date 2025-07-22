package com.mybury.waver.web.message.v1.user;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

public record UserUpdateRequest(
    @Schema(description = "이름")
    String name,
    @Schema(description = "bio")
    String bio,
    @Schema(description = "프로필 이미지", type = "string", format = "binary")
    MultipartFile profileImage
) {

}
