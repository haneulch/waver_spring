package com.mybury.waver.service;

import com.mybury.waver.common.code.ContentType;
import com.mybury.waver.common.code.ExposureStatus;
import com.mybury.waver.common.code.PremiumStatus;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.Category;
import com.mybury.waver.domain.User;
import com.mybury.waver.repository.BucketMemberRepository;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.CategoryRepository;
import com.mybury.waver.repository.FollowRepository;
import com.mybury.waver.repository.FreeTierRepository;
import com.mybury.waver.repository.LikeBucketRepository;
import com.mybury.waver.repository.ReportRepository;
import com.mybury.waver.repository.UserRepository;
import com.mybury.waver.util.FileUploadUtils;
import com.mybury.waver.web.message.v1.bucket.BucketCreateRequest;
import com.mybury.waver.web.message.v1.bucket.BucketDetailResponse;
import com.mybury.waver.web.message.v1.bucket.BucketUpdateRequest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BucketVisibilityTest {

    private final BucketRepository buckets = mock(BucketRepository.class);
    private final UserRepository users = mock(UserRepository.class);
    private final BucketService service = new BucketService(mock(FileUploadUtils.class), buckets,
            mock(LikeBucketRepository.class), mock(ReportRepository.class), users, mock(FreeTierRepository.class),
            mock(BucketMemberRepository.class), mock(CategoryRepository.class),
            new BucketAccessPolicy(mock(FollowRepository.class)), mock(ApplicationEventPublisher.class));
    private Bucket saved;

    @BeforeEach
    void setUp() {
        when(users.findById(1L)).thenReturn(Optional.of(User.builder().id(1L)
                .premiumStatus(PremiumStatus.ACTIVE).build()));
        when(buckets.findByIdAndDeleted(10L, YesNo.N)).thenAnswer(invocation -> Optional.of(saved));
        when(buckets.save(any(Bucket.class))).thenAnswer(invocation -> {
            saved = invocation.getArgument(0);
            saved.setId(10L);
            saved.setCategory(Category.builder().id(3L).name("default").build());
            saved.setComments(List.of());
            return saved;
        });
    }

    @ParameterizedTest
    @EnumSource(ExposureStatus.class)
    void togetherCreateIsAlwaysPublic(ExposureStatus requested) {
        BucketDetailResponse response = service.create(1L, createRequest(requested, "2"));

        assertThat(saved.getExposureStatus()).isEqualTo(ExposureStatus.PUBLIC);
        assertThat(response.exposureStatus()).isEqualTo(ExposureStatus.PUBLIC);
        assertThat(saved.getType()).isEqualTo(ContentType.TOGETHER);
    }

    @ParameterizedTest
    @EnumSource(ExposureStatus.class)
    void regularCreatePreservesRequestedExposure(ExposureStatus requested) {
        BucketDetailResponse response = service.create(1L, createRequest(requested, null));

        assertThat(saved.getExposureStatus()).isEqualTo(requested);
        assertThat(response.exposureStatus()).isEqualTo(requested);
    }

    @ParameterizedTest
    @EnumSource(ExposureStatus.class)
    void togetherUpdateIsAlwaysPublic(ExposureStatus requested) {
        saved = existingBucket("2");

        BucketDetailResponse response = service.update(10L, 1L, updateRequest(requested, "2"));

        assertThat(saved.getExposureStatus()).isEqualTo(ExposureStatus.PUBLIC);
        assertThat(response.exposureStatus()).isEqualTo(ExposureStatus.PUBLIC);
    }

    @ParameterizedTest
    @EnumSource(ExposureStatus.class)
    void removingParticipantsRestoresRequestedExposure(ExposureStatus requested) {
        saved = existingBucket("2");
        saved.setType(ContentType.TOGETHER);

        BucketDetailResponse response = service.update(10L, 1L, updateRequest(requested, null));

        assertThat(saved.getExposureStatus()).isEqualTo(requested);
        assertThat(response.exposureStatus()).isEqualTo(requested);
        assertThat(saved.getType()).isEqualTo(ContentType.ORIGINAL);
    }

    @ParameterizedTest
    @EnumSource(ExposureStatus.class)
    void copiedTogetherBucketsArePublic(ExposureStatus sourceExposure) {
        Bucket source = existingBucket("2");
        source.setExposureStatus(sourceExposure);

        Bucket copy = Bucket.copy(source, 3L, 4L);

        assertThat(copy.getExposureStatus()).isEqualTo(ExposureStatus.PUBLIC);
        assertThat(copy.getFriendUserIds()).isEqualTo("2");
    }

    @ParameterizedTest
    @EnumSource(ExposureStatus.class)
    void regularCopiesRemainPrivate(ExposureStatus sourceExposure) {
        Bucket source = existingBucket(null);
        source.setExposureStatus(sourceExposure);

        assertThat(Bucket.copy(source, 3L, 4L).getExposureStatus()).isEqualTo(ExposureStatus.PRIVATE);
    }

    private Bucket existingBucket(String friendIds) {
        return Bucket.builder().id(10L).userId(1L).title("old").friendUserIds(friendIds)
                .category(Category.builder().id(3L).name("default").build()).comments(List.of()).build();
    }

    private BucketCreateRequest createRequest(ExposureStatus exposure, String friendIds) {
        return new BucketCreateRequest(ContentType.ORIGINAL, exposure, "title", null, null, friendIds,
                YesNo.N, null, 1, 3L, null);
    }

    private BucketUpdateRequest updateRequest(ExposureStatus exposure, String friendIds) {
        return new BucketUpdateRequest(null, exposure, "title", null, null, friendIds,
                YesNo.N, null, 1, 3L, null);
    }
}
