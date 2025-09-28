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

@Tag(name = "배지")
@RestController
@RequestMapping("waver/badge")
@RequiredArgsConstructor
public class BadgeController {

  private final BadgeService badgeService;

  @Operation(summary = "배지 목록 조회")
  @GetMapping
  public List<BadgeResponse> getBadge(@Parameter(hidden = true) @UserId Long userId) {
    return badgeService.getBadge(userId);
  }

  @Operation(summary = "배지 선택")
  @PostMapping("{badgeId}")
  public void selectBadge(@Parameter(hidden = true) @UserId Long userId, @PathVariable long badgeId) {
    badgeService.selectBadge(userId, badgeId);
  }
}
