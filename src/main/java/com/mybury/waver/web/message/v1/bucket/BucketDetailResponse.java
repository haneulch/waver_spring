package com.mybury.waver.web.message.v1.bucket;

import com.mybury.waver.common.code.BucketStatus;
import com.mybury.waver.common.code.ExposureStatus;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.User;
import com.mybury.waver.web.message.v1.category.CategoryElement;
import com.mybury.waver.web.message.v1.comment.CommentElement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

record FriendElement(
    long id,
    String name
) {

    public FriendElement(User friend) {
        this(friend.getId(), friend.getName());
    }
}

public record BucketDetailResponse(
    long id,
    long userId,
    String title,
    String memo,
    ExposureStatus exposureStatus,
    BucketStatus status,
    YesNo pin,
    YesNo complete,
    YesNo scrapYn,
    YesNo isMine,
    YesNo isLike,
    CategoryElement category,
    int goalCount,
    int userCount,
    LocalDateTime completedDt,
    LocalDate targetDate,
    List<KeywordElement> keywords,
    List<FriendElement> friendUsers,
    List<String> images,
    List<CommentElement> comment
) {

    public static BucketDetailResponse of(Bucket bucket, long userId,
        List<KeywordElement> keywordList, List<User> friendUserList, boolean isLike) {
        CategoryElement category = new CategoryElement(bucket.getCategory());
        List<FriendElement> friendUsers = friendUserList.stream().map(
            FriendElement::new).toList();
        List<String> images =
            bucket.getImgUrl() != null ? Arrays.stream(bucket.getImgUrl().split(",")).toList() : List.of();
        List<CommentElement> comment = bucket.getComments().stream()
            .map(item -> new CommentElement(item, userId)).toList();
        return new BucketDetailResponse(
            bucket.getId(),
            bucket.getUserId(),
            bucket.getTitle(),
            bucket.getMemo(),
            bucket.getExposureStatus(),
            bucket.getStatus(),
            bucket.getPin(),
            bucket.getCompletedDate() != null ? YesNo.Y : YesNo.N,
            bucket.getScrapYn(),
            bucket.getUserId().equals(userId) ? YesNo.Y : YesNo.N,
            isLike ? YesNo.Y : YesNo.N,
            category,
            bucket.getGoalCount(),
            bucket.getUserCount(),
            bucket.getCompletedDate(),
            bucket.getTargetDate(),
            keywordList,
            friendUsers,
            images,
            comment);
    }
}
