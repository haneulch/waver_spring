package com.mybury.waver.web;

import com.mybury.waver.annotation.Public;
import com.mybury.waver.annotation.UserId;
import com.mybury.waver.common.code.PremiumStatus;
import com.mybury.waver.service.UserService;
import com.mybury.waver.web.message.v1.user.ProfileResponse;
import com.mybury.waver.web.message.v1.user.UserCreateRequest;
import com.mybury.waver.web.message.v1.user.UserUpdateRequest;
import com.mybury.waver.web.message.v1.user.WaverPlusResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Tag(name = "유저")
@RestController
@RequestMapping("waver/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @Public
  @Operation(summary = "회원가입")
  @PostMapping(value = "join", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public void createUser(@Valid @ModelAttribute UserCreateRequest request, Locale locale) {
    userService.create(request, locale);
  }

  @Operation(summary = "프로필 조회")
  @GetMapping("profile")
  public ProfileResponse getUserProfile(@Parameter(hidden = true) @UserId Long userId,
                                        @RequestParam(required = false) Long otherUserId) {
    if (otherUserId == null) {
      return userService.getMyProfile(userId);
    }
    return userService.getUserProfile(userId, otherUserId);
  }

  @Operation(summary = "프로필 수정")
  @PatchMapping(value = "profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public void patchUserProfile(@Valid @ModelAttribute UserUpdateRequest request) {
  }

  @Public
  @Operation(summary = "프로필 이름 중복 확인")
  @GetMapping("profile/name")
  public void checkUsernameAvailability(@RequestParam String name) {
    userService.checkUsernameAvailability(name);
  }


  @Operation(summary = "웨이버 플러스 제한 확인")
  @GetMapping("check/limit")
  public WaverPlusResponse checkWaverPlusLimit(@Parameter(hidden = true) @UserId Long userId) {
    PremiumStatus status = userService.checkWaverPlusLimit(userId);
    return new WaverPlusResponse(status);
  }

  @Operation(summary = "회원 탈퇴")
  @PostMapping("withdraw")
  public void withdraw(@Parameter(hidden = true) @UserId Long userId) {
    userService.withdraw(userId);
  }
}
