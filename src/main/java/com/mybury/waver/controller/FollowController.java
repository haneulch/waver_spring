package com.mybury.waver.controller;

import com.mybury.waver.annotation.UserId;
import com.mybury.waver.dto.follow.FollowRequest;
import com.mybury.waver.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "팔로우")
@RestController
@RequestMapping("waver/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @Operation(summary = "팔로우/팔로워 목록")
    @GetMapping
    public void getFollow() {

    }

    @Operation(summary = "팔로우")
    @PostMapping
    public void postFollow(@Parameter(hidden = true) @UserId Long userId, @Valid @RequestBody FollowRequest request) {
        System.out.println(userId);
        followService.follow(userId, request.followUserId());
    }

    @Operation(summary = "언팔로우")
    @PostMapping("unfollow")
    public void postUnfollow() {
    }

    @Operation(summary = "사용자 차단")
    @PostMapping("block")
    public void postBlock() {
    }

    @Operation(summary = "사용자 차단 해제")
    @PostMapping("block/release")
    public void postBlockRelease() {
    }

    @Operation(summary = "차단된 사용자 목록")
    @GetMapping("blockUsers")
    public void getBlockUsers() {
    }

    @Operation(summary = "맞팔목록")
    @GetMapping("mutual")
    public void getMutual() {
    }
}

