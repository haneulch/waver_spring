package com.mybury.waver.web;

import com.mybury.waver.annotation.UserId;
import com.mybury.waver.service.SubscribeService;
import com.mybury.waver.web.message.v1.subscribe.SubscribeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "구독")
@RestController
@RequestMapping("waver/subscribe")
@RequiredArgsConstructor
public class SubscriptionController {

  private final SubscribeService subscribeService;

  @Operation
  @PostMapping
  public void subscribe(@Parameter(hidden = true) @UserId Long userId, @Valid @RequestBody SubscribeRequest request) {
    subscribeService.subscribe(userId, request.subscribeId());
  }
}
