package com.mybury.waver.repository;

import com.mybury.waver.domain.Comment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndUserId(Long id, Long userId);

}
