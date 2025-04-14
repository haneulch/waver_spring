package com.mybury.waver.controller;

import com.mybury.waver.annotation.UserId;
import com.mybury.waver.dto.comment.CommentCreateRequest;
import com.mybury.waver.dto.comment.CommentCreateResponse;
import com.mybury.waver.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Operation(summary = "Get Test")
    @GetMapping
    public CommentCreateResponse comment(@Valid @ParameterObject CommentCreateRequest request) {
        commentService.test();
        return new CommentCreateResponse("23323GET");
    }

    @Operation(summary = "Post Test")
    @PostMapping
    public CommentCreateResponse comment2(@Parameter(hidden = true) @UserId Long userId,
        @Valid @RequestBody CommentCreateRequest request) {
        try {
            return new CommentCreateResponse("23323POST");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
