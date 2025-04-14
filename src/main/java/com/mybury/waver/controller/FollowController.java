package com.mybury.waver.controller;

import com.mybury.waver.annotation.UserId;
import com.mybury.waver.dto.follow.FollowRequest;
import com.mybury.waver.service.FollowService;
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

    @GetMapping
    public void getFollow() {

    }

    @PostMapping
    public void postFollow(@Parameter(hidden = true) @UserId Long userId, @Valid @RequestBody FollowRequest request) {
        System.out.println(userId);
        followService.follow(userId, request.followUserId());
    }

    @PostMapping("unfollow")
    public void postUnfollow() {
    }

    @PostMapping("block")
    public void postBlock() {
    }

    @PostMapping("block/release")
    public void postBlockRelease() {
    }

    @GetMapping("blockUsers")
    public void getBlockUsers() {
    }

    @GetMapping("mutual")
    public void getMutual() {
    }
}

