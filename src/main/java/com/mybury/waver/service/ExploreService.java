package com.mybury.waver.service;

import com.mybury.waver.domain.RecentSearch;
import com.mybury.waver.repository.RecentSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExploreService {
  private final RecentSearchRepository recentSearchRepository;

  public void search(long userId, String query) {
    addRecentSearch(userId, query);
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
