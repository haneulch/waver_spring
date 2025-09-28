package com.mybury.waver.web;

import com.mybury.waver.annotation.Public;
import com.mybury.waver.domain.vo.LoginProjection;
import com.mybury.waver.security.JwtTokenProvider;
import com.mybury.waver.service.UserService;
import com.mybury.waver.web.message.v1.main.LoginRequest;
import com.mybury.waver.web.message.v1.main.LoginResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Main")
@RestController
@RequestMapping("waver")
@RequiredArgsConstructor
public class MainController {

  private final JwtTokenProvider jwtTokenProvider;
  private final UserService userService;

  @Public
  @PostMapping("login")
  public LoginResponse login(@Valid @RequestBody LoginRequest request) {
    LoginProjection login = userService.getUserIdByUid(request.uid());
    String token = jwtTokenProvider.generateToken(login.getId());
    return new LoginResponse(token, login.getPremiumStatus());
  }
}
