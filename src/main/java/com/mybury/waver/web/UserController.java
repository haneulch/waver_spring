package com.mybury.waver.web;

import com.mybury.waver.annotation.Public;
import com.mybury.waver.annotation.UserId;
import com.mybury.waver.common.code.PremiumStatus;
import com.mybury.waver.security.JwtTokenProvider;
import com.mybury.waver.service.UserService;
import com.mybury.waver.web.message.v1.main.LoginResponse;
import com.mybury.waver.web.message.v1.user.ProfileResponse;
import com.mybury.waver.web.message.v1.user.UserCreateRequest;
import com.mybury.waver.web.message.v1.user.UserUpdateRequest;
import com.mybury.waver.web.message.v1.user.WaverPlusResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저")
@RestController
@RequestMapping("waver/user")
@RequiredArgsConstructor
public class UserController {

  private final JwtTokenProvider jwtTokenProvider;
  private final UserService userService;

  @Public
  @Operation(summary = "회원가입")
  @PostMapping(value = "join", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public LoginResponse createUser(@Valid @ModelAttribute UserCreateRequest request, Locale locale) {
    long userId = userService.create(request, locale);
    String token = jwtTokenProvider.generateToken(userId);
    return new LoginResponse(token, PremiumStatus.NONE);
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
  public void patchUserProfile(@Parameter(hidden = true) @UserId Long userId,
      @Valid @ModelAttribute UserUpdateRequest request) {
    userService.modify(userId, request.profileImage(), request.name(), request.bio());
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
