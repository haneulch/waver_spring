package com.mybury.waver.controller;

import com.mybury.waver.annotation.UserId;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.dto.bucket.BucketCreateRequest;
import com.mybury.waver.dto.bucket.BucketRequest;
import com.mybury.waver.dto.bucket.BucketResponse;
import com.mybury.waver.service.BucketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "버킷리스트")
@RestController
@RequestMapping("waver/bucket")
@RequiredArgsConstructor
public class BucketController {
  private final BucketService bucketService;

  @Operation(summary = "버킷리스트 등록")
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public void bucketCreate(@Parameter(hidden = true) @UserId Long userId, @Valid @ModelAttribute BucketCreateRequest request) {
    bucketService.create(userId, request);
  }

  @Operation(summary = "버킷리스트 목록")
  @GetMapping
  public BucketResponse bucketList(@Parameter(hidden = true) @UserId Long userId, @Valid @ParameterObject BucketRequest request) {
    List<Bucket> bucketList = bucketService.bucketList(userId, request);
    return BucketResponse.of(bucketList);
  }

  @Operation(summary = "버킷리스트 수정")
  @PostMapping("{id}")
  public void bucketUpdate(@PathVariable Long id) {
  }

  @Operation(summary = "버킷리스트 상세")
  @GetMapping("{id}")
  public void bucketDetail(@PathVariable Long id) {
  }

  @Operation(summary = "버킷리스트 삭제")
  @DeleteMapping("{id}")
  public void bucketDelete(@PathVariable Long id) {
  }

  @Operation(summary = "버킷리스트 달성")
  @GetMapping("{id}/achieve")
  public void bucketAchieve(@PathVariable Long id) {
  }

  @Operation(summary = "버킷리스트 달성취소")
  @GetMapping("{id}/achieve/cancel")
  public void bucketAchieveCancel(@PathVariable Long id) {
  }

  @Operation(summary = "버킷리스트 다시 도전하기")
  @GetMapping("{id}/reset")
  public void bucketReset(@PathVariable Long id) {
  }

  @Operation(summary = "버킷리스트 달성횟수 수정")
  @PatchMapping("{id}/goalCount")
  public void bucketGoalCountPatch(@PathVariable Long id) {
  }
}
