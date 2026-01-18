package com.mybury.waver.web.message.v1.subscribe;

import jakarta.validation.constraints.NotBlank;

public record SubscribeRequest(
    @NotBlank
    String subscribeId
) {

}
