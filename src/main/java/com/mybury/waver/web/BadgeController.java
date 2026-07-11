package com.mybury.waver.web;

import com.mybury.waver.annotation.UserId;
import com.mybury.waver.service.BadgeService;
import com.mybury.waver.web.message.v1.badge.BadgeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "배지", description = "키워드 활동으로 획득하는 배지 조회·선택")
@RestController
@RequestMapping("waver/badge")
@RequiredArgsConstructor
public class BadgeController {

  private final BadgeService badgeService;

  @Operation(summary = "내 배지 목록",
      description = "보유 배지와 달성 진행도를 반환합니다. 배지는 키워드가 달린 버킷을 등록할수록 성장합니다.")
  @GetMapping
  public List<BadgeResponse> getBadge(@Parameter(hidden = true) @UserId Long userId) {
    return badgeService.getBadge(userId);
  }

  @Operation(summary = "대표 배지 선택",
      description = "프로필에 노출할 대표 배지를 지정합니다. 선택할 수 없는 배지는 7000(BADGE_CANNOT_SELECT)을 반환합니다.")
  @PostMapping("{badgeId}")
  public void selectBadge(@Parameter(hidden = true) @UserId Long userId, @PathVariable long badgeId) {
    badgeService.selectBadge(userId, badgeId);
  }
}
