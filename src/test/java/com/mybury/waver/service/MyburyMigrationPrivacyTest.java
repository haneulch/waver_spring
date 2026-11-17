package com.mybury.waver.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybury.waver.common.code.ExposureStatus;
import com.mybury.waver.common.code.MigrationStatus;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Badge;
import com.mybury.waver.domain.BadgeType;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.MigrationInfo;
import com.mybury.waver.domain.User;
import com.mybury.waver.domain.mybury.MyburyBucketlist;
import com.mybury.waver.domain.mybury.MyburyUser;
import com.mybury.waver.repository.BadgeRepository;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.CategoryRepository;
import com.mybury.waver.repository.FollowRepository;
import com.mybury.waver.repository.MigrationInfoRepository;
import com.mybury.waver.repository.MyburyBucketlistRepository;
import com.mybury.waver.repository.MyburyCategoryRepository;
import com.mybury.waver.repository.MyburyUserRepository;
import com.mybury.waver.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MyburyMigrationPrivacyTest {

    @Test
    void migratedBucketsStayPrivateAndAreExcludedFromOtherProfiles() {
        MigrationInfoRepository migrations = mock(MigrationInfoRepository.class);
        UserRepository users = mock(UserRepository.class);
        CategoryRepository categories = mock(CategoryRepository.class);
        BucketRepository buckets = mock(BucketRepository.class);
        MyburyUserRepository legacyUsers = mock(MyburyUserRepository.class);
        MyburyBucketlistRepository legacyBuckets = mock(MyburyBucketlistRepository.class);
        List<Bucket> saved = new ArrayList<>();
        User owner = User.builder().id(1L).email("owner@example.test").bucketlist(saved).build();
        MigrationInfo migration = MigrationInfo.request(1L);
        MyburyUser legacyUser = mock(MyburyUser.class);
        when(legacyUser.getId()).thenReturn("legacy-owner");
        when(migrations.findById(7L)).thenReturn(Optional.of(migration));
        when(users.findById(1L)).thenReturn(Optional.of(owner));
        when(legacyUsers.findByEmail(owner.getEmail())).thenReturn(Optional.of(legacyUser));
        when(categories.findIdByUserIdAndDefaultYn(1L, YesNo.Y)).thenReturn(3L);
        MyburyBucketlist completed = mock(MyburyBucketlist.class);
        when(completed.getTitle()).thenReturn("completed");
        when(completed.getCompletedDt()).thenReturn(LocalDateTime.of(2026, 11, 1, 12, 0));
        MyburyBucketlist progress = mock(MyburyBucketlist.class);
        when(progress.getTitle()).thenReturn("progress");
        when(legacyBuckets.findByUserIdOrderByOrderSeqAsc("legacy-owner"))
                .thenReturn(List.of(completed, progress));
        when(buckets.save(any(Bucket.class))).thenAnswer(invocation -> {
            Bucket bucket = invocation.getArgument(0);
            bucket.setId(100L + saved.size());
            saved.add(bucket);
            return bucket;
        });
        MyburyMigrationService migrationService = new MyburyMigrationService(migrations, users, categories,
                buckets, legacyUsers, mock(MyburyCategoryRepository.class), legacyBuckets);

        migrationService.migrateUser(7L);

        assertThat(migration.getStatus()).isEqualTo(MigrationStatus.COMPLETED);
        assertThat(saved).hasSize(2).allSatisfy(bucket -> {
            assertThat(bucket.getExposureStatus()).isEqualTo(ExposureStatus.PRIVATE);
            assertThat(bucket.isTogether()).isFalse();
        });
        BadgeRepository badges = mock(BadgeRepository.class);
        when(badges.findByUserIdAndSelectYn(1L, YesNo.Y)).thenReturn(Optional.of(Badge.builder()
                .user(owner).badgeType(BadgeType.builder().title("badge").build()).build()));
        FollowRepository follows = mock(FollowRepository.class);
        MyService profiles = new MyService(buckets, follows, badges, users, new BucketAccessPolicy(follows));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode stranger = mapper.valueToTree(profiles.getOther(1L, 2L)).path("bucketInfo");
        JsonNode own = mapper.valueToTree(profiles.getOther(1L, 1L)).path("bucketInfo");

        assertThat(stranger.path("bucketlist").size()).isZero();
        assertThat(stranger.path("totalCount").asInt()).isZero();
        assertThat(stranger.path("completedCount").asInt()).isZero();
        assertThat(stranger.path("progressCount").asInt()).isZero();
        assertThat(own.path("totalCount").asInt()).isEqualTo(2);
        assertThat(own.path("completedCount").asInt()).isEqualTo(1);
        assertThat(own.path("progressCount").asInt()).isEqualTo(1);
    }
}
