package com.mybury.waver.service;

import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.Follow;
import com.mybury.waver.domain.RecentSearch;
import com.mybury.waver.domain.User;
import com.mybury.waver.dto.explore.ExploreResponse;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.FollowRepository;
import com.mybury.waver.repository.RecentSearchRepository;
import com.mybury.waver.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    List<User> users = userRepository.findByNameLike(query);
    List<Follow> follows = followRepository.findByUserIdOrFollowUserId(userId, userId);

    Set<Long> followIds = follows.stream().map(Follow::getFollowUserId).collect(Collectors.toSet());
    Set<Long> followerIds = follows.stream().map(Follow::getUserId).collect(Collectors.toSet());
    Set<Long> mutualIds = new HashSet<>(followIds);
    mutualIds.retainAll(followerIds);

    return new ExploreResponse(buckets, users, mutualIds);
  }

  public void deleteAllSearchData(long userId) {
    recentSearchRepository.deleteRecentSearchByUserId(userId);
  }

  public void deleteSearchData(long userId, String keyword) {
    recentSearchRepository.deleteRecentSearchByUserIdAndQuery(userId, keyword);
  }

  @Async
  protected void addRecentSearch(long userId, String keyword) {
    recentSearchRepository.save(RecentSearch.create(userId, keyword));
  }
}
