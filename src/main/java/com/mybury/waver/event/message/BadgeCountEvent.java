package com.mybury.waver.event.message;

import java.util.List;

public record BadgeCountEvent(
    long userId,
    List<String> keywords
) {

}
