package com.mybury.waver.controller;

import com.mybury.waver.dto.comment.CommentCreateRequest;
import com.mybury.waver.dto.comment.CommentCreateResponse;
import com.mybury.waver.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public CommentCreateResponse commentCreate(@Valid @ParameterObject CommentCreateRequest request) {
        commentService.test();
        return new CommentCreateResponse("23323GET");
    }

    @Operation(summary = "댓글수정")
    @PatchMapping("{id}")
    public void commentUpdate(@PathVariable Long id) {
    }

    @Operation(summary = "댓글삭제")
    @DeleteMapping("{id}")
    public void commentDelete(@PathVariable Long id) {
    }

    @Operation(summary = "댓글신고")
    @PatchMapping("{id}/report")
    public void commentReport(@PathVariable Long id) {
    }

    @Operation(summary = "댓글숨기기")
    @PatchMapping("{id}/hide")
    public void commentHide(@PathVariable Long id) {
    }
}
