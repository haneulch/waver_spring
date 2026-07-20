package com.mybury.waver.web;

import com.mybury.waver.annotation.UserId;
import com.mybury.waver.service.CommentService;
import com.mybury.waver.web.message.v1.comment.CommentCreateRequest;
import com.mybury.waver.web.message.v1.comment.CommentUpdateRequest;
import com.mybury.waver.web.message.v1.common.ReportRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "댓글", description = "버킷리스트 댓글 작성·수정·삭제·숨기기")
@RestController
@RequestMapping("waver/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 등록",
        description = "버킷리스트에 댓글을 남깁니다. mentionIds로 멘션할 사용자를 지정할 수 있고, 버킷 작성자에게 푸시 알림이 발송됩니다.")
    @PostMapping
    public void commentCreate(@Parameter(hidden = true) @UserId Long userId,
        @Valid @RequestBody CommentCreateRequest request) {
        commentService.commentCreate(userId, request);
    }

    @Operation(summary = "댓글 수정", description = "본인이 작성한 댓글만 수정할 수 있습니다(아니면 4040).")
    @PatchMapping("{id}")
    public void commentUpdate(@Parameter(hidden = true) @UserId Long userId, @PathVariable Long id, @Valid @RequestBody CommentUpdateRequest request) {
        commentService.commentUpdate(id, userId, request);
    }

    @Operation(summary = "댓글 삭제", description = "본인이 작성한 댓글만 삭제할 수 있습니다(아니면 4040).")
    @DeleteMapping("{id}")
    public void commentDelete(@Parameter(hidden = true) @UserId Long userId, @PathVariable Long id) {
        commentService.commentDelete(id, userId);
    }

    @Operation(summary = "댓글 숨기기", description = "댓글을 목록에서 보이지 않게 처리합니다.")
    @PatchMapping("{id}/hide")
    public void commentHide(@Parameter(hidden = true) @UserId Long userId, @PathVariable Long id) {
        commentService.commentHide(id, userId);
    }

    @Operation(summary = "댓글 신고",
        description = "댓글을 신고합니다. 신고한 댓글은 이후 본인에게 미노출되며, 누적 3회 신고되면 모든 사용자에게 미노출(차단) 처리됩니다. 중복 신고는 무시됩니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "4040", description = "NOT_FOUND — 댓글을 찾을 수 없음"),
        @ApiResponse(responseCode = "4030", description = "FORBIDDEN — 자기 댓글은 신고 불가")
    })
    @PatchMapping("{id}/report")
    public void commentReport(@Parameter(hidden = true) @UserId Long userId, @PathVariable Long id,
        @RequestBody ReportRequest request) {
        commentService.report(id, request.reason(), userId);
    }
}
