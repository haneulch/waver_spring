package com.mybury.waver.event.message;

import com.mybury.waver.common.code.AlarmMessageType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AlarmMessageEvent(
    @NotNull
    AlarmMessageType type,
    @Positive
    long userId,
    long otherUserId,
    long bucketId,
    String message
) {

}
