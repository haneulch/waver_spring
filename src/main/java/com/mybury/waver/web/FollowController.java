package com.mybury.waver.web;

import com.mybury.waver.annotation.UserId;
import com.mybury.waver.domain.Follow;
import com.mybury.waver.service.FollowService;
import com.mybury.waver.web.message.v1.follow.FollowElement;
import com.mybury.waver.web.message.v1.follow.FollowRequest;
import com.mybury.waver.web.message.v1.follow.GetFollowersResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "팔로우")
@RestController
@RequestMapping("waver/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @Operation(summary = "팔로우/팔로워 목록")
    @GetMapping
    public GetFollowersResponse getFollow(@Parameter(hidden = true) @UserId Long userId) {
        return followService.getFollowList(userId);
    }

    @Operation(summary = "팔로우")
    @PostMapping
    public void postFollow(@Parameter(hidden = true) @UserId Long userId, @Valid @RequestBody FollowRequest request) {
        followService.follow(userId, request.followUserId());
    }

    @Operation(summary = "언팔로우")
    @PostMapping("unfollow")
    public void postUnfollow(@Parameter(hidden = true) @UserId Long userId, @Valid @RequestBody FollowRequest request) {
        followService.unfollow(userId, request.followUserId());
    }

    @Operation(summary = "맞팔목록")
    @GetMapping("mutual")
    public List<FollowElement> getMutual(@Parameter(hidden = true) @UserId Long userId,
        @RequestParam(required = false) String name) {
        List<Follow> follows = followService.getMutual(userId, name);
        return follows.stream().map(FollowElement::new).toList();
    }
}

