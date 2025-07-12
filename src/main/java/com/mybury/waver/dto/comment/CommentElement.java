package com.mybury.waver.dto.comment;

import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Comment;

public record CommentElement(
    long id,
    YesNo isMyComment,
    long userId,
    String imgUrl,
    String name,
    String content,
    YesNo isBlocked
) {
  public CommentElement(Comment comment, long userId) {
    this(
        comment.getId(),
        comment.getUserId().equals(userId) ? YesNo.Y : YesNo.N,
        comment.getUserId(),
        comment.getUser().getImgUrl(),
        comment.getUser().getName(),
        comment.getComment(),
        comment.getIsBlocked()
    );
  }
}
