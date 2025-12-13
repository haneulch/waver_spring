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

  public static AlarmMessageEvent feedLike(long userId, long likedUserId) {
    return new AlarmMessageEvent(AlarmMessageType.FEED_LIKE, userId, likedUserId, null, null, null);
  }

  public static AlarmMessageEvent feedComment(long userId, long commentUserId) {
    return new AlarmMessageEvent(AlarmMessageType.FEED_COMMENT, userId, commentUserId, null, null, null);
  }

  public static AlarmMessageEvent dday(long userId, String bucketTitle) {
    return new AlarmMessageEvent(AlarmMessageType.D_DAY_7, userId, null, null, bucketTitle, null);
  }
}
