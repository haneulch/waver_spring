package com.mybury.waver.event.message;

import com.mybury.waver.alarm.code.AlarmMessageType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AlarmMessageEvent(
    @NotNull
    AlarmMessageType type,
    @Positive
    long userId,
    long otherUserId,
    String otherUserName,
    long bucketId,
    String bucketTitle,
    String message
) {

}
