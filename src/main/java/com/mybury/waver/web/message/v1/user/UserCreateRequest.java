package com.mybury.waver.web.message.v1.user;

import com.mybury.waver.common.code.AccountType;
import com.mybury.waver.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

public record UserCreateRequest(
    @NotEmpty
    @Schema(description = "이메일")
    String email,
    @NotBlank
    String uid,
    @NotNull
    @Schema(description = "기기유형", implementation = AccountType.class)
    AccountType accountType,
    @NotEmpty
    @Schema(description = "이름")
    String name,
    @Schema(description = "bio")
    String bio,
    @Schema(description = "프로필 이미지", type = "string", format = "binary")
    MultipartFile profileImage,
    @Schema(description = "FCM Token")
    String fcmToken
) {

  public User user(Locale locale) {
    return User.builder()
        .uid(uid)
        .email(email)
        .accountType(accountType)
        .name(name)
        .bio(bio)
        .locale(locale)
        .fcmToken(fcmToken)
        .build();
  }
}
