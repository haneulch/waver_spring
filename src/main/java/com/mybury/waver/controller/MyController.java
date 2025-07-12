package com.mybury.waver.controller;

import com.mybury.waver.annotation.UserId;
import com.mybury.waver.dto.my.MyResponse;
import com.mybury.waver.dto.my.MyWaveInfoResponse;
import com.mybury.waver.service.MyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "마이")
@RestController
@RequestMapping("waver/my")
@RequiredArgsConstructor
public class MyController {

  private final MyService myService;

  @Operation(summary = "마이페이지 메인")
  @GetMapping
  public MyResponse my(@Parameter(hidden = true) @UserId Long userId) {
    return myService.my(userId);
  }

  @Operation(summary = "타인 > 마이페이지 메인")
  @GetMapping("{otherUserId}")
  public MyResponse otherMy(@PathVariable Long otherUserId) {
    return myService.my(otherUserId);
  }

  @Operation(summary = "푸시 목록")
  @GetMapping("push")
  public void push() {
  }

  @Operation(summary = "푸시 메세지 테스트")
  @GetMapping("pushTest")
  public void pushTest() {
  }

  @Operation(summary = "내 웨이브")
  @GetMapping("info")
  public MyWaveInfoResponse info(@Parameter(hidden = true) @UserId Long userId) {
    return myService.waveInfo(userId);
  }
}
