package com.mybury.waver.web.message.v1.feed;

import com.mybury.waver.common.code.BucketStatus;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.web.message.v1.user.UserElement;
import java.util.Arrays;
import java.util.List;

public record FeedResponse(
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

    public static FeedResponse of(Bucket bucket) {
        UserElement user = UserElement.of(bucket.getUser());
        if (bucket.getImgUrl() != null) {
            List<String> images = Arrays.stream(bucket.getImgUrl().split(",")).toList();
            return new FeedResponse(bucket.getId(), bucket.getStatus(), bucket.getTitle(), images, user,
                bucket.getLikeCount(), bucket.getComments().size(), YesNo.N, bucket.getScrapYn());
        }
        return new FeedResponse(bucket.getId(), bucket.getStatus(), bucket.getTitle(), null, user,
            bucket.getLikeCount(), bucket.getComments().size(), YesNo.N, bucket.getScrapYn());
    }
}
