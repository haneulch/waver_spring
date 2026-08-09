package com.mybury.waver.web;

import com.mybury.waver.annotation.Public;
import com.mybury.waver.annotation.UserId;
import com.mybury.waver.common.code.PremiumStatus;
import com.mybury.waver.domain.User;
import com.mybury.waver.security.JwtTokenProvider;
import com.mybury.waver.service.UserService;
import com.mybury.waver.web.message.v1.main.LoginResponse;
import com.mybury.waver.web.message.v1.user.ProfileResponse;
import com.mybury.waver.web.message.v1.user.UserCreateRequest;
import com.mybury.waver.web.message.v1.user.UserStatusRequest;
import com.mybury.waver.web.message.v1.user.UserStatusResponse;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저", description = "회원가입, 프로필, 탈퇴, 이용 제한 조회")
@RestController
@RequestMapping("waver/user")
@RequiredArgsConstructor
public class UserController {

  private final JwtTokenProvider jwtTokenProvider;
  private final UserService userService;

  @Public
  @Operation(summary = "회원가입",
      description = "신규 가입 후 accessToken을 즉시 발급합니다. 기본 카테고리·배지·무료 이용 혜택이 함께 생성됩니다. "
          + "이메일 또는 이름이 중복이면 6001을 반환합니다.")
  @PostMapping(value = "join", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public LoginResponse createUser(@Valid @ModelAttribute UserCreateRequest request, Locale locale) {
    User user = userService.create(request, locale);
    String token = jwtTokenProvider.generateToken(user.getId());
    return new LoginResponse(token, PremiumStatus.NONE, user.getMyburyYn());
  }

  @Operation(summary = "mybury 데이터 이관 요청",
      description = "mybury 기존 회원(myburyYn=Y)만 요청할 수 있습니다. 요청된 사용자는 스케줄러가 순차적으로 데이터를 이관합니다. "
          + "mybury 회원이 아니면 8200, 이미 이관 완료면 8201, 이미 요청됐으면 8202를 반환합니다.")
  @PostMapping("migration")
  public void requestMigration(@Parameter(hidden = true) @UserId Long userId) {
    userService.requestMigration(userId);
  }

  @Operation(summary = "프로필 조회",
      description = "otherUserId를 생략하면 내 프로필, 지정하면 해당 사용자의 프로필(팔로우 여부 포함)을 반환합니다.")
  @GetMapping("profile")
  public ProfileResponse getUserProfile(@Parameter(hidden = true) @UserId Long userId,
      @RequestParam(required = false) Long otherUserId) {
    if (otherUserId == null) {
      return userService.getMyProfile(userId);
    }
    return userService.getUserProfile(userId, otherUserId);
  }

  @Operation(summary = "프로필 수정",
      description = "전달된 필드만 수정합니다. 프로필 이미지, 이름, 소개(bio)를 선택적으로 보낼 수 있습니다.")
  @PatchMapping(value = "profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public void patchUserProfile(@Parameter(hidden = true) @UserId Long userId,
      @Valid @ModelAttribute UserUpdateRequest request) {
    userService.modify(userId, request.profileImage(), request.name(), request.bio());
  }

  @Public
  @Operation(summary = "프로필 이름 중복 확인",
      description = "사용 가능하면 200, 이미 사용 중이면 6001(EMAIL_OR_NAME_CANNOT_DUPLICATE)을 반환합니다.")
  @GetMapping("profile/name")
  public void checkUsernameAvailability(@RequestParam String name) {
    userService.checkUsernameAvailability(name);
  }


  @Operation(summary = "구독 상태·무료 이용 잔여 횟수 조회",
      description = "premiumStatus(NONE/ACTIVE/EXPIRED)와 무료 혜택 제공/사용 횟수"
          + "(imageLimit·imageUsed, togetherLimit·togetherUsed)를 반환합니다.")
  @GetMapping("check/limit")
  public WaverPlusResponse checkWaverPlusLimit(@Parameter(hidden = true) @UserId Long userId) {
    return userService.checkWaverPlusLimit(userId);
  }

  @Operation(summary = "회원 탈퇴",
      description = "계정을 탈퇴 처리하고 작성한 버킷리스트를 모두 삭제 상태로 전환합니다.")
  @PostMapping("withdraw")
  public void withdraw(@Parameter(hidden = true) @UserId Long userId) {
    userService.withdraw(userId);
  }

  @Public
  @Operation(summary = "사용자 상태 확인",
      description = "email+uid로 계정 상태(ACTIVE 등), 마지막 로그인/탈퇴 일시를 반환합니다. 탈퇴한 계정도 조회됩니다.")
  @PostMapping("status")
  public UserStatusResponse status(@Valid @RequestBody UserStatusRequest request) {
    return userService.status(request.email(), request.uid());
  }

  @Operation(summary = "FCM 토큰 업데이트",
      description = "푸시 알림 수신용 FCM 토큰을 갱신합니다. 앱 시작 또는 토큰 변경 시 호출하세요.")
  @PostMapping("fcm-token")
  public void updateFcmToken(@Parameter(hidden = true) @UserId Long userId, @RequestBody String fcmToken) {
    userService.updateFcmToken(userId, fcmToken);
  }
}
