package com.mybury.waver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "피드")
@RestController
@RequestMapping("waver/feeds")
@RequiredArgsConstructor
public class FeedController {

    @Operation(summary = "피드")
    @GetMapping
    public void feeds() {
    }

    @Operation(summary = "관신 키워드 저장")
    @PostMapping("keyword")
    public void keyword() {
    }

    @Operation(summary = "피드 좋아요/취소")
    @PostMapping("{id}/like")
    public void like(@PathVariable Long id) {
    }

    @Operation(summary = "피드 스크랩")
    @PostMapping("{id}/scrap")
    public void scrap(@PathVariable Long id) {
    }

    @Operation(summary = "피드 신고")
    @PostMapping("{id}/report")
    public void report(@PathVariable Long id) {
    }
}
