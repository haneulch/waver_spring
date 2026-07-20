package com.mybury.waver.repository;

import com.mybury.waver.domain.Comment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndUserId(Long id, Long userId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Comment SET isBlocked = com.mybury.waver.common.code.YesNo.Y WHERE id = :id")
    void markBlockedById(Long id);

}
