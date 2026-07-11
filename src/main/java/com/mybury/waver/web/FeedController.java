package com.mybury.waver.web;

import com.mybury.waver.annotation.UserId;
import com.mybury.waver.event.message.FeedLikeEvent;
import com.mybury.waver.service.FeedService;
import com.mybury.waver.web.message.v1.common.ReportRequest;
import com.mybury.waver.web.message.v1.feed.FeedCopyResponse;
import com.mybury.waver.web.message.v1.feed.FeedResponse;
import com.mybury.waver.web.message.v1.feed.KeywordRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "피드", description = "관심 키워드 기반 피드, 좋아요, 스크랩, 신고")
@RestController
@RequestMapping("waver/feeds")
@RequiredArgsConstructor
public class FeedController {

  private final FeedService feedService;
  private final ApplicationEventPublisher publisher;

  @Operation(summary = "피드 조회",
      description = "다른 사용자의 공개(또는 맞팔 공개) 버킷을 20개씩 반환합니다. 좋아요 여부(likeYn) 포함. "
          + "hasNext=true면 nextKey로 다음 페이지를 요청하세요. 관심 키워드 저장이 선행되어야 합니다.")
  @ApiResponse(responseCode = "8000", description = "KEYWORD_NOT_FOUND — 저장된 관심 키워드 없음")
  @GetMapping
  public FeedResponse feeds(
    @Parameter(hidden = true) @UserId Long userId,
    @RequestParam(required = false) Long nextKey) {
    List<FeedResponse.FeedElement> feeds = feedService.feeds(userId, nextKey);
    return FeedResponse.of(feeds);
  }

  @Operation(summary = "관심 키워드 저장", description = "피드 노출 기준이 되는 관심 키워드 코드 목록을 저장합니다.")
  @PostMapping("keyword")
  public void keyword(@Parameter(hidden = true) @UserId Long userId, @Valid @RequestBody KeywordRequest request) {
    feedService.keyword(userId, request.keywordCodes());
  }

  @Operation(summary = "좋아요/취소 (토글)",
      description = "같은 API를 다시 호출하면 취소됩니다. 좋아요 시 버킷 작성자에게 푸시 알림이 발송됩니다.")
  @PostMapping("{id}/like")
  public void like(@PathVariable Long id, @Parameter(hidden = true) @UserId Long userId) {
    publisher.publishEvent(new FeedLikeEvent(id, userId));
  }

  @Operation(summary = "스크랩(내 버킷으로 복사)",
      description = "스크랩 허용(scrapYn=Y)된 버킷을 내 기본 카테고리로 복사하고 새 버킷 ID를 반환합니다.")
  @PostMapping("{id}/scrap")
  public FeedCopyResponse copy(@PathVariable Long id, @Parameter(hidden = true) @UserId Long userId) {
    long copiedId = feedService.copy(userId, id);
    return new FeedCopyResponse(copiedId);
  }

  @Operation(summary = "피드 신고",
      description = "버킷을 신고합니다. 신고한 버킷은 이후 목록·피드에서 제외되며, 누적 3회 신고되면 자동 삭제 처리됩니다. 중복 신고는 무시됩니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "4040", description = "NOT_FOUND — 버킷을 찾을 수 없음"),
    @ApiResponse(responseCode = "4030", description = "FORBIDDEN — 자기 버킷은 신고 불가")
  })
  @PostMapping("{id}/report")
  public void report(@PathVariable Long id, @RequestBody ReportRequest request,
                     @Parameter(hidden = true) @UserId Long userId) {
    feedService.report(id, request.reason(), userId);
  }
}
