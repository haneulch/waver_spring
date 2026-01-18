package com.mybury.waver.event;

import com.mybury.waver.domain.LikeBucket;
import com.mybury.waver.event.message.AlarmMessageEvent;
import com.mybury.waver.event.message.FeedLikeEvent;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.LikeBucketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 피드 좋아요 이벤트
 */
@Component
@RequiredArgsConstructor
public class FeedLikeEventListener {

  private final BucketRepository bucketRepository;
  private final LikeBucketRepository likeBucketRepository;
  private final ApplicationEventPublisher publisher;

  @Transactional
  @EventListener
  public void handle(FeedLikeEvent event) {
    long id = event.id();
    long userId = event.userId();

    boolean isLiked = likeBucketRepository.existsByUserIdAndBucketId(userId, id);
    bucketRepository.updateLike(id, isLiked ? -1 : 1);

    if (isLiked) {
      likeBucketRepository.deleteByUserIdAndBucketId(userId, id);
    } else {
      LikeBucket likeBucket = LikeBucket.builder().userId(userId).bucketId(id).build();
      likeBucketRepository.save(likeBucket);

      Long bucketUserId = bucketRepository.findUserIdById(id);
      if (bucketUserId != null) {
        publisher.publishEvent(AlarmMessageEvent.feedLike(bucketUserId, userId));
      }
    }
  }
}
