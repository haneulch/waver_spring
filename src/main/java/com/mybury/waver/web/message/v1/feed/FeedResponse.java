package com.mybury.waver.web.message.v1.feed;

import com.mybury.waver.common.code.BucketStatus;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.util.FileImageUtils;
import com.mybury.waver.web.message.v1.user.UserElement;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

public record FeedResponse(
  boolean hasNext,
  Long nextKey,
  List<FeedElement> data
) {

  public static FeedResponse of(List<FeedElement> feeds) {
    if (CollectionUtils.isEmpty(feeds)) {
      return new FeedResponse(false, null, null);
    }
    if (feeds.size() > 20) {
      return new FeedResponse(true, feeds.getLast().id(), feeds.subList(0, 20));
    }
    return new FeedResponse(false, null, feeds);
  }


  public record FeedElement(
    long id,
    BucketStatus status,
    String title,
    List<String> images,
    UserElement user,
    int like,
    int commentCount,
    YesNo likeYn,
    YesNo scrapYn
  ) {
    public static FeedElement of(Bucket bucket) {
      return of(bucket, YesNo.N);
    }

    public static FeedElement of(Bucket bucket, YesNo likeYn) {
      UserElement user = UserElement.of(bucket.getUser());
      if (bucket.getImgUrl() != null) {
        List<String> images = Arrays.stream(bucket.getImgUrl().split(","))
          .map(FileImageUtils::imagePath)
          .toList();
        return new FeedElement(bucket.getId(), bucket.getStatus(), bucket.getTitle(), images, user,
          bucket.getLikeCount(), bucket.getComments().size(), likeYn, bucket.getScrapYn());
      }
      return new FeedElement(bucket.getId(), bucket.getStatus(), bucket.getTitle(), null, user,
        bucket.getLikeCount(), bucket.getComments().size(), likeYn, bucket.getScrapYn());
    }
  }
}
