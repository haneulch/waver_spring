package com.mybury.waver.controller;

import com.mybury.waver.annotation.UserId;
import com.mybury.waver.dto.category.CategoryCreateRequest;
import com.mybury.waver.dto.category.CategoryNameRequest;
import com.mybury.waver.dto.category.CategoryOrderRequest;
import com.mybury.waver.dto.category.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "카테고리")
@RestController
@RequestMapping("waver/category")
@RequiredArgsConstructor
public class CategoryController {

  @Operation(summary = "카테고리 목록")
  @GetMapping
  public List<CategoryResponse> getCategory(@Parameter(hidden = true) @UserId Long userId, @RequestParam(required = false) String query) {
    return null;
  }

  @Operation(summary = "카테고리 추가")
  @PostMapping
  public void postCategory(@Parameter(hidden = true) @UserId Long userId, @Valid @RequestBody CategoryCreateRequest request) {
  }

  @Operation(summary = "카테고리 이름 변경")
  @PatchMapping("{id}")
  public void patchCategory(@PathVariable Long id, @Parameter(hidden = true) @UserId Long userId, @Valid @RequestBody CategoryNameRequest request) {
  }

  @Operation(summary = "카테고리 삭제")
  @DeleteMapping("{id}")
  public void deleteCategory(@PathVariable Long id, @Parameter(hidden = true) @UserId Long userId) {
  }

  @Operation(summary = "카테고리 순서 변경")
  @PatchMapping("seq")
  public void patchCategorySeq(@Parameter(hidden = true) @UserId Long userId, @Valid @RequestBody CategoryOrderRequest request) {
  }

  @Operation(summary = "추천 카테고리 목록 조회")
  @GetMapping("recommend")
  public void recommendCategory(@Parameter(hidden = true) @UserId Long userId) {
  }
}
