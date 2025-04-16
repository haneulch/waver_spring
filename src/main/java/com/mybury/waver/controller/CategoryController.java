package com.mybury.waver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "카테고리")
@RestController
@RequestMapping("waver/category")
@RequiredArgsConstructor
public class CategoryController {

    @Operation(summary = "카테고리 목록")
    @GetMapping
    public void getCategory() {
    }

    @Operation(summary = "카테고리 추가")
    @PostMapping
    public void postCategory() {
    }

    @Operation(summary = "카테고리 이름 변경")
    @PatchMapping
    public void patchCategory() {
    }

    @Operation(summary = "카테고리 삭제")
    @DeleteMapping("{id}")
    public void deleteCategory(@PathVariable Long id) {
    }

    @Operation(summary = "카테고리 순서 변경")
    @PatchMapping("seq")
    public void patchCategorySeq() {
    }

    @Operation(summary = "추천 카테고리 목록 조회")
    @GetMapping("recommend")
    public void recommendCategory() {
    }
}
