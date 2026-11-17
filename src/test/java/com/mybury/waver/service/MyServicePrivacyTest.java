package com.mybury.waver.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybury.waver.common.code.BucketStatus;
import com.mybury.waver.common.code.ExposureStatus;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Badge;
import com.mybury.waver.domain.BadgeType;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.User;
import com.mybury.waver.repository.BadgeRepository;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.FollowRepository;
import com.mybury.waver.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MyServicePrivacyTest {

    private final FollowRepository follows = mock(FollowRepository.class);
    private final BadgeRepository badges = mock(BadgeRepository.class);
    private final MyService service = new MyService(mock(BucketRepository.class), follows, badges,
            mock(UserRepository.class), new BucketAccessPolicy(follows));

    @BeforeEach
    void setUp() {
        User owner = User.builder().id(1L).bucketlist(List.of(
                bucket(10L, ExposureStatus.PUBLIC, YesNo.N, BucketStatus.COMPLETE),
                bucket(11L, ExposureStatus.FOLLOWER, YesNo.N, BucketStatus.COMPLETE),
                bucket(12L, ExposureStatus.PRIVATE, YesNo.N, BucketStatus.PROGRESS),
                bucket(13L, ExposureStatus.PUBLIC, YesNo.Y, BucketStatus.PROGRESS))).build();
        Badge selected = Badge.builder().user(owner).badgeType(BadgeType.builder().title("badge").build()).build();
        when(badges.findByUserIdAndSelectYn(1L, YesNo.Y)).thenReturn(java.util.Optional.of(selected));
    }

    @ParameterizedTest
    @CsvSource({"false,false,1", "true,false,1", "false,true,1", "true,true,2"})
    void profileOnlyIncludesViewableBucketsAndCounts(boolean viewerFollows, boolean ownerFollows, int count) {
        when(follows.existsByUserIdAndFollowUserId(2L, 1L)).thenReturn(viewerFollows);
        when(follows.existsByUserIdAndFollowUserId(1L, 2L)).thenReturn(ownerFollows);

        JsonNode info = new ObjectMapper().valueToTree(service.getOther(1L, 2L)).path("bucketInfo");

        assertThat(info.path("bucketlist").findValuesAsText("id"))
                .containsExactlyElementsOf(count == 2 ? List.of("10", "11") : List.of("10"));
        assertThat(info.path("totalCount").asInt()).isEqualTo(count);
        assertThat(info.path("completedCount").asInt()).isEqualTo(count);
        assertThat(info.path("progressCount").asInt()).isZero();
    }

    @Test
    void ownerCanSeePrivateBucketsButNotDeletedBuckets() {
        JsonNode info = new ObjectMapper().valueToTree(service.getOther(1L, 1L)).path("bucketInfo");

        assertThat(info.path("bucketlist").findValuesAsText("id")).containsExactly("10", "11", "12");
        assertThat(info.path("totalCount").asInt()).isEqualTo(3);
        assertThat(info.path("completedCount").asInt()).isEqualTo(2);
        assertThat(info.path("progressCount").asInt()).isEqualTo(1);
    }

    private Bucket bucket(long id, ExposureStatus exposure, YesNo deleted, BucketStatus status) {
        return Bucket.builder().id(id).userId(1L).title("bucket").exposureStatus(exposure)
                .deleted(deleted).status(status).build();
    }
}
