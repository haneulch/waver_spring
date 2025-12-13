package com.mybury.waver.service;

import com.mybury.waver.domain.Subscribe;
import com.mybury.waver.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscribeService {

  private final SubscribeRepository subscribeRepository;

  public void subscribe(long userId, String subscribeId) {
    Subscribe subscribe = Subscribe.builder()
        .userId(userId)
        .subscribeId(subscribeId)
        .build();
    subscribeRepository.save(subscribe);
  }
}
