package com.mybury.waver.web;

import com.mybury.waver.annotation.UserId;
import com.mybury.waver.service.ExploreService;
import com.mybury.waver.web.message.v1.explore.ExploreResponse;
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

@Tag(name = "탐색", description = "버킷·사용자 통합 검색과 최근 검색어, 추천 키워드")
@RestController
@RequestMapping("waver/explore")
@RequiredArgsConstructor
public class ExploreController {

    private final ExploreService exploreService;

    @Operation(summary = "통합 검색",
        description = "제목이 일치하는 공개 버킷과 이름이 일치하는 사용자를 함께 반환합니다. 검색어는 최근 검색어에 자동 저장됩니다.")
    @GetMapping
    public ExploreResponse explore(@Parameter(hidden = true) @UserId Long userId, @RequestParam String query) {
        return exploreService.search(userId, query);
    }

    @Operation(summary = "키워드 전체 목록", description = "선택 가능한 고정 키워드 전체를 반환합니다.")
    @GetMapping("keywords")
    public List<KeywordResponse> keywords() {
        return KeywordResponse.getAllKeywords();
    }

    @Operation(summary = "검색 초기 화면 데이터", description = "최근 검색어 목록과 랜덤 추천 키워드를 반환합니다.")
    @GetMapping("searchOptions")
    public SearchOptionResponse searchOptions(@Parameter(hidden = true) @UserId Long userId) {
        return exploreService.searchOptions(userId);
    }

    @Operation(summary = "최근 검색어 전체 삭제", description = "내 최근 검색어를 모두 지웁니다.")
    @DeleteMapping("recentSearch/all")
    public void recentSearchAll(@Parameter(hidden = true) @UserId Long userId) {
        exploreService.deleteAllSearchData(userId);
    }

    @Operation(summary = "최근 검색어 개별 삭제", description = "지정한 검색어 하나만 삭제합니다.")
    @DeleteMapping("recentSearch/{keyword}")
    public void recentSearch(@Parameter(hidden = true) @UserId Long userId, @PathVariable String keyword) {
        exploreService.deleteSearchData(userId, keyword);
    }
}
