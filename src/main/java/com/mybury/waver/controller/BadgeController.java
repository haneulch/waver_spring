package com.mybury.waver.controller;

import com.mybury.waver.annotation.UserId;
import com.mybury.waver.dto.badge.BadgeResponse;
import com.mybury.waver.service.BadgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
