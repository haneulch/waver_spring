package com.mybury.waver.web;

import com.mybury.waver.annotation.UserId;
import com.mybury.waver.service.CommentService;
import com.mybury.waver.web.message.v1.comment.CommentCreateRequest;
import com.mybury.waver.web.message.v1.comment.CommentUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Tag(name = "댓글")
@RestController
@RequestMapping("waver/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글등록")
    @PostMapping
    public void commentCreate(@Parameter(hidden = true) @UserId Long userId,
        @Valid @RequestBody CommentCreateRequest request) {
        commentService.commentCreate(userId, request);
    }

    @Operation(summary = "댓글수정")
    @PatchMapping("{id}")
    public void commentUpdate(@Parameter(hidden = true) @UserId Long userId, @PathVariable Long id, @Valid @RequestBody CommentUpdateRequest request) {
        commentService.commentUpdate(id, userId, request);
    }

    @Operation(summary = "댓글삭제")
    @DeleteMapping("{id}")
    public void commentDelete(@Parameter(hidden = true) @UserId Long userId, @PathVariable Long id) {
        commentService.commentDelete(id, userId);
    }

    @Operation(summary = "댓글숨기기")
    @PatchMapping("{id}/hide")
    public void commentHide(@Parameter(hidden = true) @UserId Long userId, @PathVariable Long id) {
        commentService.commentHide(id, userId);
    }
}
