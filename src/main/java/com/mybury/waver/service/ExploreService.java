package com.mybury.waver.service;

import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.Follow;
import com.mybury.waver.domain.RecentSearch;
import com.mybury.waver.domain.User;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.FollowRepository;
import com.mybury.waver.repository.RecentSearchRepository;
import com.mybury.waver.repository.UserRepository;
import com.mybury.waver.web.message.v1.explore.ExploreResponse;
import com.mybury.waver.web.message.v1.explore.SearchOptionResponse;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExploreService {

  private final RecentSearchRepository recentSearchRepository;
  private final BucketRepository bucketRepository;
  private final FollowRepository followRepository;
  private final UserRepository userRepository;

  @Transactional
  public ExploreResponse search(long userId, String query) {
    addRecentSearch(userId, query);

    List<Bucket> buckets = bucketRepository.search(query);
    List<User> users = userRepository.findByNameContaining(query);
    List<Follow> follows = followRepository.findByUserIdOrFollowUserId(userId, userId);

    Set<Long> followIds = new HashSet<>();

    for (Follow follow : follows) {
      if (java.util.Objects.equals(follow.getUserId(), userId) && follow.getFollowUser().getDeleteYn() == YesNo.N) {
        followIds.add(follow.getFollowUserId());
      }
    }

    return new ExploreResponse(buckets, users, followIds);
  }

  public void deleteAllSearchData(long userId) {
    recentSearchRepository.deleteRecentSearchByUserId(userId);
  }

  public void deleteSearchData(long userId, String keyword) {
    recentSearchRepository.deleteRecentSearchByUserIdAndQuery(userId, keyword);
  }

  @Async
  public void addRecentSearch(long userId, String keyword) {
    recentSearchRepository.save(RecentSearch.create(userId, keyword));
  }

  public SearchOptionResponse searchOptions(Long userId) {
    List<String> recentSearch = recentSearchRepository.findQueryByUserId(userId);
    List<SearchOptionResponse.KeywordElement> recommendedKeywords = SearchOptionResponse.KeywordElement
        .getAllKeywords();
    return new SearchOptionResponse(recentSearch, null, recommendedKeywords);
  }
}
