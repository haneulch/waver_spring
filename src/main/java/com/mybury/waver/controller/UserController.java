package com.mybury.waver.controller;

import com.mybury.waver.annotation.Public;
import com.mybury.waver.annotation.UserId;
import com.mybury.waver.dto.user.UserCreateRequest;
import com.mybury.waver.dto.user.UserUpdateRequest;
import com.mybury.waver.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    private final UserService userService;

    @Public
    @Operation(summary = "회원가입")
    @PostMapping(value = "join", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void createUser(@Valid @ModelAttribute UserCreateRequest request) {
        userService.create(request);
    }

    @Operation(summary = "프로필 조회")
    @GetMapping("profile")
    public void getUserProfile(@Parameter(hidden = true) @UserId Long userId) {

    }

    @Operation(summary = "프로필 수정")
    @PatchMapping(value = "profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void patchUserProfile(@Valid @ModelAttribute UserUpdateRequest request) {
    }

    @Public
    @Operation(summary = "프로필 이름 중복 확인")
    @GetMapping("profile/name")
    public void checkUsernameAvailability(@RequestParam String name) {
    }


    @Operation(summary = "웨이버 플러스 제한 확인")
    @GetMapping("check/limit")
    public void checkWaverPlusLimit(@Parameter(hidden = true) @UserId Long userId) {
    }
}
