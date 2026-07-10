package com.mybury.waver.web;

import com.mybury.waver.annotation.UserId;
import com.mybury.waver.domain.Category;
import com.mybury.waver.service.CategoryService;
import com.mybury.waver.web.message.v1.category.CategoryCreateRequest;
import com.mybury.waver.web.message.v1.category.CategoryNameRequest;
import com.mybury.waver.web.message.v1.category.CategoryOrderRequest;
import com.mybury.waver.web.message.v1.category.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
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

@Tag(name = "카테고리")
@RestController
@RequestMapping("waver/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 목록")
    @GetMapping
    public List<CategoryResponse> getCategory(@Parameter(hidden = true) @UserId Long userId,
        @RequestParam(required = false) String query) {
        List<Category> categories = categoryService.getCategory(userId, query);
        return categories.stream().map(CategoryResponse::new).toList();
    }

    @Operation(summary = "카테고리 추가")
    @PostMapping
    public void postCategory(@Parameter(hidden = true) @UserId Long userId,
        @Valid @RequestBody CategoryCreateRequest request) {
        categoryService.postCategory(userId, request.name());
    }

    @Operation(summary = "카테고리 이름 변경")
    @PatchMapping("{id}")
    public void patchCategory(@PathVariable Long id, @Parameter(hidden = true) @UserId Long userId,
        @Valid @RequestBody CategoryNameRequest request) {
        categoryService.patchCategory(id, userId, request.name());
    }

    @Operation(summary = "카테고리 삭제")
    @DeleteMapping("{id}")
    public void deleteCategory(@PathVariable Long id, @Parameter(hidden = true) @UserId Long userId) {
        categoryService.deleteCategory(id, userId);
    }

    @Operation(summary = "카테고리 순서 변경")
    @PatchMapping("seq")
    public void patchCategorySeq(@Parameter(hidden = true) @UserId Long userId,
        @Valid @RequestBody CategoryOrderRequest request) {
        categoryService.patchCategorySeq(userId, request.categoryIds());
    }
}
