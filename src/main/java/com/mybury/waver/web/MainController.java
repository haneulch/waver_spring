package com.mybury.waver.web;

import com.mybury.waver.annotation.Public;
import com.mybury.waver.domain.vo.LoginProjection;
import com.mybury.waver.security.JwtTokenProvider;
import com.mybury.waver.service.UserService;
import com.mybury.waver.web.message.v1.main.LoginRequest;
import com.mybury.waver.web.message.v1.main.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증", description = "로그인과 액세스 토큰 발급")
@RestController
@RequestMapping("waver")
@RequiredArgsConstructor
public class MainController {

  private final JwtTokenProvider jwtTokenProvider;
  private final UserService userService;

  @Public
  @Operation(summary = "로그인",
      description = "소셜 인증 uid로 로그인하고 accessToken과 구독 상태를 반환합니다. 인증 헤더가 필요 없는 공개 API입니다.",
      responses = {
          @ApiResponse(responseCode = "4040", description = "NOT_FOUND — 가입되지 않은 uid"),
          @ApiResponse(responseCode = "9000", description = "WITHDRAWAL_USER — 탈퇴한 사용자")
      })
  @PostMapping("login")
  public LoginResponse login(@Valid @RequestBody LoginRequest request) {
    LoginProjection login = userService.getUserIdByUid(request.uid());
    String token = jwtTokenProvider.generateToken(login.getId());
    return new LoginResponse(token, login.getPremiumStatus());
  }
}
