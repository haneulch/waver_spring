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

@Tag(name = "팔로우", description = "팔로우/언팔로우와 팔로워·맞팔 목록")
@RestController
@RequestMapping("waver/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @Operation(summary = "팔로잉/팔로워 목록",
        description = "내가 팔로우하는 사용자와 나를 팔로우하는 사용자 목록을 함께 반환합니다. 탈퇴한 사용자는 제외됩니다.")
    @GetMapping
    public GetFollowersResponse getFollow(@Parameter(hidden = true) @UserId Long userId) {
        return followService.getFollowList(userId);
    }

    @Operation(summary = "팔로우", description = "대상 사용자를 팔로우합니다.")
    @PostMapping
    public void postFollow(@Parameter(hidden = true) @UserId Long userId, @Valid @RequestBody FollowRequest request) {
        followService.follow(userId, request.followUserId());
    }

    @Operation(summary = "언팔로우", description = "대상 사용자 팔로우를 해제합니다.")
    @PostMapping("unfollow")
    public void postUnfollow(@Parameter(hidden = true) @UserId Long userId, @Valid @RequestBody FollowRequest request) {
        followService.unfollow(userId, request.followUserId());
    }

    @Operation(summary = "맞팔로우 목록",
        description = "서로 팔로우 중인 사용자 목록입니다. name으로 이름 검색이 가능하며, 함께하기 친구 선택 화면에서 사용합니다.")
    @GetMapping("mutual")
    public List<FollowElement> getMutual(@Parameter(hidden = true) @UserId Long userId,
        @RequestParam(required = false) String name) {
        List<Follow> follows = followService.getMutual(userId, name);
        return follows.stream().map(FollowElement::new).toList();
    }
}

