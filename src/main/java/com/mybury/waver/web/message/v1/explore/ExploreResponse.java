package com.mybury.waver.web.message.v1.explore;

import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.User;
import java.util.List;
import java.util.Set;

record BucketElement(
    long id,
    long userId,
    String title,
    YesNo scrapYn
) {

  public BucketElement(Bucket bucket) {
    this(bucket.getId(), bucket.getUserId(), bucket.getTitle(), bucket.getScrapYn());
  }
}

record UserElement(
    long id,
    String name,
    String imgUrl,
    YesNo followYn
) {

  public UserElement(User user, Set<Long> followedIds) {
    this(user.getId(), user.getName(), user.getImgUrl(), followedIds.contains(user.getId()) ? YesNo.Y : YesNo.N);
  }
}

public record ExploreResponse(
    List<BucketElement> bucketlist,
    List<UserElement> users
) {

  public ExploreResponse(List<Bucket> buckets, List<User> users, Set<Long> followedIds) {
    this(
        buckets.stream().map(BucketElement::new).toList(),
        users.stream().map(user -> new UserElement(user, followedIds)).toList()
    );
  }
}
