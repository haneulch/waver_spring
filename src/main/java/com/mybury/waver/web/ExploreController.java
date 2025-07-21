package com.mybury.waver.web;

import com.mybury.waver.annotation.UserId;
import com.mybury.waver.service.ExploreService;
import com.mybury.waver.web.message.v1.explore.KeywordResponse;
import com.mybury.waver.web.message.v1.explore.SearchOptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "검색")
@RestController
@RequestMapping("waver/explore")
@RequiredArgsConstructor
public class ExploreController {

    private final ExploreService exploreService;

    @Operation(summary = "검색")
    @GetMapping
    public void explore(@Parameter(hidden = true) @UserId Long userId, @RequestParam String query) {
        exploreService.search(userId, query);
    }

    @Operation(summary = "키워드 전체 목록 조회")
    @GetMapping("keywords")
    public List<KeywordResponse> keywords() {
        return KeywordResponse.getAllKeywords();
    }

    @Operation(summary = "최근 검색어 & 추천 키워드 조회")
    @GetMapping("searchOptions")
    public SearchOptionResponse searchOptions(@Parameter(hidden = true) @UserId Long userId) {
        return exploreService.searchOptions(userId);
    }

    @Operation(summary = "최근 검색어 전체 삭제")
    @DeleteMapping("recentSearch/all")
    public void recentSearchAll(@Parameter(hidden = true) @UserId Long userId) {
        exploreService.deleteAllSearchData(userId);
    }

    @Operation(summary = "최근 검색어 삭제")
    @DeleteMapping("recentSearch/{keyword}")
    public void recentSearch(@Parameter(hidden = true) @UserId Long userId, @PathVariable String keyword) {
        exploreService.deleteSearchData(userId, keyword);
    }
}
