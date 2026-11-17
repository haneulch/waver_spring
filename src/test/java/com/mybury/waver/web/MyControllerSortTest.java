package com.mybury.waver.web;

import com.mybury.waver.annotation.UserIdArgumentResolver;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Badge;
import com.mybury.waver.domain.BadgeType;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.User;
import com.mybury.waver.repository.BadgeRepository;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.FollowRepository;
import com.mybury.waver.repository.UserRepository;
import com.mybury.waver.security.JwtTokenProvider;
import com.mybury.waver.service.AlarmService;
import com.mybury.waver.service.BucketAccessPolicy;
import com.mybury.waver.service.MyService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MyControllerSortTest {

    private final BucketRepository buckets = mock(BucketRepository.class);
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        BadgeRepository badges = mock(BadgeRepository.class);
        FollowRepository follows = mock(FollowRepository.class);
        User owner = User.builder().id(1L).bucketlist(List.of(Bucket.builder().id(99L).build())).build();
        when(badges.findByUserIdAndSelectYn(1L, YesNo.Y)).thenReturn(Optional.of(Badge.builder().user(owner)
                .badgeType(BadgeType.builder().title("badge").build()).build()));
        MyService service = new MyService(buckets, follows, badges, mock(UserRepository.class),
                new BucketAccessPolicy(follows));
        JwtTokenProvider tokens = mock(JwtTokenProvider.class);
        when(tokens.extractToken("Bearer test-token")).thenReturn("test-token");
        when(tokens.getUserIdFromToken("test-token")).thenReturn(1L);
        mvc = MockMvcBuilders.standaloneSetup(new MyController(service, mock(AlarmService.class)))
                .setCustomArgumentResolvers(new UserIdArgumentResolver(tokens)).build();
    }

    @ParameterizedTest
    @CsvSource({
            "CREATED,createdAt,ASC",
            "CREATED_DESC,createdAt,DESC",
            "UPDATED,updatedAt,ASC",
            "UPDATED_DESC,updatedAt,DESC",
            "LIKE_COUNT_DESC,likeCount,DESC"
    })
    void acceptsEveryExistingBucketSort(String sort, String field, Sort.Direction direction) throws Exception {
        assertSortedRequest(get("/waver/my").param("sort", sort), Sort.by(direction, field, "id"));
    }

    @Test
    void omittedSortDefaultsToNewestCreated() throws Exception {
        assertSortedRequest(get("/waver/my"), Sort.by(Sort.Direction.DESC, "createdAt", "id"));
    }

    private void assertSortedRequest(MockHttpServletRequestBuilder request, Sort expected) throws Exception {
        when(buckets.findByUserIdAndDeleted(1L, YesNo.N, expected)).thenReturn(List.of(
                Bucket.builder().id(20L).title("first from query").build(),
                Bucket.builder().id(10L).title("second from query").build()));

        mvc.perform(request.header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bucketInfo.bucketlist[0].id").value(20))
                .andExpect(jsonPath("$.bucketInfo.bucketlist[1].id").value(10))
                .andExpect(jsonPath("$.bucketInfo.totalCount").value(2));

        verify(buckets).findByUserIdAndDeleted(1L, YesNo.N, expected);
    }
}
