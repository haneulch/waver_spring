package com.mybury.waver.web.message.v1.bucket;

import com.mybury.waver.common.code.BucketStatus;
import com.mybury.waver.domain.Bucket;
import java.util.List;

public record BucketResponse(
    List<BucketElement> bucketlist,
    int totalCount,
    int completedCount,
    int progressCount
) {

    public static BucketResponse of(List<Bucket> bucketList) {
        List<BucketElement> bucketlist = bucketList.stream().map(BucketElement::new).toList();
        int totalCount = bucketlist.size();
        int completedCount = (int) bucketlist.stream().filter(bucket -> bucket.status() == BucketStatus.COMPLETE)
            .count();

        return new BucketResponse(bucketlist, totalCount, completedCount, totalCount - completedCount);
    }
}
