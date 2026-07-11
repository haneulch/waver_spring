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

@Tag(name = "카테고리", description = "버킷리스트 분류 카테고리 관리")
@RestController
@RequestMapping("waver/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 목록", description = "내 카테고리를 순서대로 반환합니다. query로 이름 검색이 가능합니다.")
    @GetMapping
    public List<CategoryResponse> getCategory(@Parameter(hidden = true) @UserId Long userId,
        @RequestParam(required = false) String query) {
        List<Category> categories = categoryService.getCategory(userId, query);
        return categories.stream().map(CategoryResponse::new).toList();
    }

    @Operation(summary = "카테고리 추가", description = "이름이 중복이면 6000(CATEGORY_CANNOT_DUPLICATE)을 반환합니다.")
    @PostMapping
    public void postCategory(@Parameter(hidden = true) @UserId Long userId,
        @Valid @RequestBody CategoryCreateRequest request) {
        categoryService.postCategory(userId, request.name());
    }

    @Operation(summary = "카테고리 이름 변경", description = "내 카테고리의 이름을 변경합니다.")
    @PatchMapping("{id}")
    public void patchCategory(@PathVariable Long id, @Parameter(hidden = true) @UserId Long userId,
        @Valid @RequestBody CategoryNameRequest request) {
        categoryService.patchCategory(id, userId, request.name());
    }

    @Operation(summary = "카테고리 삭제", description = "카테고리를 삭제합니다. 소속 버킷 처리 방식은 서비스 정책을 따릅니다.")
    @DeleteMapping("{id}")
    public void deleteCategory(@PathVariable Long id, @Parameter(hidden = true) @UserId Long userId) {
        categoryService.deleteCategory(id, userId);
    }

    @Operation(summary = "카테고리 순서 변경", description = "categoryIds 배열 순서대로 노출 순서를 재정렬합니다.")
    @PatchMapping("seq")
    public void patchCategorySeq(@Parameter(hidden = true) @UserId Long userId,
        @Valid @RequestBody CategoryOrderRequest request) {
        categoryService.patchCategorySeq(userId, request.categoryIds());
    }
}
