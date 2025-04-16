package com.mybury.waver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "검색")
@RestController
@RequestMapping("waver/explore")
@RequiredArgsConstructor
public class ExploreController {

    @Operation(summary = "검색")
    @GetMapping
    public void explore() {
    }

    @Operation(summary = "키워드 전체 목록 조회")
    @GetMapping("keywords")
    public void keywords() {
    }

    @Operation(summary = "최근 검색어 & 추천 키워드 조회")
    @GetMapping("searchOptions")
    public void searchOptions() {
    }

    @Operation(summary = "최근 검색어 전체 삭제")
    @DeleteMapping("recentSearch/all")
    public void recentSearchAll() {
    }

    @Operation(summary = "최근 검색어 삭제")
    @DeleteMapping("recentSearch/{keyword}")
    public void recentSearch(@PathVariable String keyword) {
    }
}
