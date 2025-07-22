package com.mybury.waver.service;

import com.mybury.waver.domain.Comment;
import com.mybury.waver.repository.CommentRepository;
import com.mybury.waver.web.message.v1.comment.CommentCreateRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public void commentCreate(Long userId, @Valid CommentCreateRequest request) {
        Comment comment = Comment.builder()
            .userId(userId)
            .comment(request.content())
            .bucketId(request.bucketId())
            .mentionIds(String.join(",", request.mentionIds())).build();
        commentRepository.save(comment);
    }
}
