package com.mybury.waver.web;

import com.mybury.waver.annotation.UserId;
import com.mybury.waver.service.BucketService;
import com.mybury.waver.web.message.v1.bucket.BucketCreateRequest;
import com.mybury.waver.web.message.v1.bucket.BucketDetailResponse;
import com.mybury.waver.web.message.v1.bucket.BucketGoalCountRequest;
import com.mybury.waver.web.message.v1.bucket.BucketRequest;
import com.mybury.waver.web.message.v1.bucket.BucketResponse;
import com.mybury.waver.web.message.v1.bucket.BucketUpdateRequest;
import com.mybury.waver.web.message.v1.bucket.GetPopularBucketResponse;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "버킷리스트", description = "버킷리스트 등록·조회·수정·삭제와 달성 관리")
@RestController
@RequestMapping("waver/bucket")
@RequiredArgsConstructor
public class BucketController {

    private final BucketService bucketService;

    @Operation(summary = "버킷리스트 등록",
        description = "이미지는 최대 3장. 무료 사용자는 기본 1장이며 2장 이상 저장은 1회만 제공됩니다(초과 시 8100). "
            + "함께하기(TOGETHER)는 무료 3회 제공됩니다(초과 시 8101). 키워드 등록 시 배지 달성 횟수가 올라갑니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BucketDetailResponse bucketCreate(@Parameter(hidden = true) @UserId Long userId,
        @Valid @ModelAttribute BucketCreateRequest request) {
        return bucketService.create(userId, request);
    }

    @Operation(summary = "버킷리스트 목록",
        description = "필터·정렬 조건으로 목록을 조회합니다. hasMyBucket=Y이면 내 버킷과 내가 참여자인 함께하기 버킷이 함께 반환됩니다. "
            + "함께하기 버킷은 참여자 각자의 카테고리로 노출되고 진행도(userCount)/상태(status)는 조회자 본인 기준입니다. "
            + "신고한 버킷은 자동 제외됩니다.")
    @GetMapping
    public BucketResponse bucketList(@Parameter(hidden = true) @UserId Long userId,
        @Valid @ParameterObject BucketRequest request) {
        return bucketService.bucketList(userId, request);
    }

    @Operation(summary = "인기 버킷리스트 조회",
        description = "최근 한 달 기준 인기(좋아요순) 4개와 추천(최신순) 4개, 각 키워드 목록을 반환합니다.")
    @GetMapping("popular")
    public GetPopularBucketResponse popularBucket(@Parameter(hidden = true) @UserId Long userId) {
        return bucketService.popularBucket(userId);
    }

    @Operation(summary = "버킷리스트 수정",
        description = "소유자만 수정할 수 있습니다(아니면 4030). 이미지를 보내면 교체, 생략하면 기존 이미지가 유지됩니다. "
            + "bucketType을 생략하면 기존 종류가 유지됩니다.")
    @PostMapping(value = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BucketDetailResponse bucketUpdate(@Parameter(hidden = true) @UserId Long userId, @PathVariable Long id,
        @Valid @ModelAttribute BucketUpdateRequest request) {
        return bucketService.update(id, userId, request);
    }

    @Operation(summary = "버킷리스트 상세",
        description = "키워드, 함께하는 친구 목록, 좋아요 여부, 댓글까지 포함한 상세 정보를 반환합니다. "
            + "함께하기 버킷은 userCount/status/complete가 조회자 본인 기준이며, "
            + "friendUsers에는 본인을 제외한 참여자 전원(소유자 포함)이 개인 진행도와 함께 담깁니다.")
    @GetMapping("{id}")
    public BucketDetailResponse bucketDetail(@Parameter(hidden = true) @UserId Long userId, @PathVariable Long id) {
        return bucketService.bucketDetail(id, userId);
    }

    @Operation(summary = "버킷리스트 삭제", description = "소유자의 버킷을 삭제 상태로 전환합니다(soft delete).")
    @DeleteMapping("{id}")
    public void bucketDelete(@Parameter(hidden = true) @UserId Long userId, @PathVariable Long id) {
        bucketService.delete(id, userId);
    }

    @Operation(summary = "버킷리스트 달성(횟수 +1)",
        description = "달성 횟수를 1 올립니다. 목표 횟수에 도달하면 COMPLETE 상태로 전환됩니다. "
            + "소유자와 함께하기(TOGETHER) 참여자만 호출할 수 있습니다(아니면 4030). "
            + "함께하기 버킷은 호출자 본인의 진행도만 올라가며, 완성 시 다른 참여자에게 알림이 발송됩니다.")
    @GetMapping("{id}/achieve")
    public void bucketAchieve(@Parameter(hidden = true) @UserId Long userId, @PathVariable Long id) {
        bucketService.achieve(id, userId);
    }

    @Operation(summary = "버킷리스트 달성 취소(횟수 -1)",
        description = "달성 횟수를 1 내립니다. 소유자와 함께하기 친구만 호출할 수 있습니다.")
    @GetMapping("{id}/achieve/cancel")
    public void bucketAchieveCancel(@Parameter(hidden = true) @UserId Long userId, @PathVariable Long id) {
        bucketService.achieveCancel(id, userId);
    }

    @Operation(summary = "버킷리스트 다시 도전하기",
        description = "달성 횟수를 0으로 초기화하고 진행중(PROGRESS) 상태로 되돌립니다.")
    @GetMapping("{id}/reset")
    public void bucketReset(@Parameter(hidden = true) @UserId Long userId, @PathVariable Long id) {
        bucketService.reset(id, userId);
    }

    @Operation(summary = "목표 횟수 수정", description = "버킷리스트의 목표 달성 횟수(goalCount)를 변경합니다.")
    @PatchMapping("{id}/goalCount")
    public void bucketGoalCountPatch(@Parameter(hidden = true) @UserId Long userId, @PathVariable Long id,
        @Valid @RequestBody BucketGoalCountRequest request) {
        bucketService.patchGoalCount(id, userId, request.goalCount());
    }
}
