package com.mybury.waver.web.message.v1.my;

import com.mybury.waver.common.code.BucketStatus;
import com.mybury.waver.domain.Badge;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.User;
import com.mybury.waver.web.message.v1.bucket.BucketElement;
import java.util.List;


record BucketInfo(
    List<BucketElement> bucketlist,
    int totalCount,
    int completedCount,
    int progressCount
) {

    public static BucketInfo of(List<Bucket> buckets) {
        List<BucketElement> bucketlist = buckets.stream().map(BucketElement::new).toList();
        int totalCount = bucketlist.size();
        int completedCount = (int) bucketlist.stream().filter(bucket -> bucket.status() == BucketStatus.COMPLETE)
            .count();
        return new BucketInfo(bucketlist, totalCount, completedCount,
            totalCount - completedCount);
    }
}

public record MyResponse(
    long id,
    String imgUrl,
    String name,
    String bio,
    String badgeTitle,
    int followingCount,
    int followerCount,
    BucketInfo bucketInfo
) {

    public MyResponse(User user, Badge selected, int followingCount, int followerCount) {
        this(user.getId(), user.getImgUrl(), user.getName(), user.getBio(), selected.getBadgeType().getTitle(),
            followingCount, followerCount, BucketInfo.of(user.getBucketlist()));
    }
}
