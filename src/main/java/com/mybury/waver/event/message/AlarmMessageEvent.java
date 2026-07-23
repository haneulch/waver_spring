package com.mybury.waver.event.message;

import com.mybury.waver.alarm.code.AlarmMessageType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AlarmMessageEvent(
    @NotNull
    AlarmMessageType type,
    @Positive
    long userId,
    Long otherUserId,
    Long bucketId,
    String bucketTitle,
    String message
) {

  public static AlarmMessageEvent notice(long userId) {
    return new AlarmMessageEvent(AlarmMessageType.NOTICE, userId, null, null, null, null);
  }

  public static AlarmMessageEvent feedLike(long userId, long likedUserId, Long bucketId) {
    return new AlarmMessageEvent(AlarmMessageType.FEED_LIKE, userId, likedUserId, bucketId, null, null);
  }

  public static AlarmMessageEvent feedComment(long userId, long commentUserId, Long bucketId) {
    return new AlarmMessageEvent(AlarmMessageType.FEED_COMMENT, userId, commentUserId, bucketId, null, null);
  }

  public static AlarmMessageEvent dday(long userId, Long bucketId, String bucketTitle) {
    return new AlarmMessageEvent(AlarmMessageType.D_DAY_7, userId, null, bucketId, bucketTitle, null);
  }

  public static AlarmMessageEvent event(long userId, String content) {
    return new AlarmMessageEvent(AlarmMessageType.EVENT, userId, null, null, null, content);
  }

  public static AlarmMessageEvent follow(long userId, long followerUserId) {
    return new AlarmMessageEvent(AlarmMessageType.FOLLOW, userId, followerUserId, null, null, null);
  }

  public static AlarmMessageEvent badge(long userId, String badgeTitle) {
    return new AlarmMessageEvent(AlarmMessageType.BADGE, userId, null, null, null, badgeTitle);
  }

  public static AlarmMessageEvent together(long userId, long completedUserId, Long bucketId,
      String bucketTitle) {
    return new AlarmMessageEvent(AlarmMessageType.TOGETHER, userId, completedUserId, bucketId,
        bucketTitle, null);
  }
}
