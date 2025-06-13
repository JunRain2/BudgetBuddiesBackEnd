package com.prography.budgetbuddiesbackend.report.domain.category.controller;

import com.prography.budgetbuddiesbackend.common.response.ApiResponse;
import com.prography.budgetbuddiesbackend.report.domain.category.controller.dto.request.RegisterCategoryRequest;
import com.prography.budgetbuddiesbackend.report.domain.category.controller.dto.response.UserCategoryResponse;
import com.prography.budgetbuddiesbackend.report.domain.category.service.CategoryFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryFacadeService categoryFacadeService;

    // 카테고리 생성
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createCategory(@RequestBody RegisterCategoryRequest request,
                                                           @RequestParam Long userId) {
        categoryFacadeService.registerCategory(request, userId);
        return ResponseEntity.ok(ApiResponse.success());
    }

    // 카테고리 삭제
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long categoryId,
                                                           @RequestParam Long userId) {
        categoryFacadeService.deleteCategory(categoryId, userId);
        return ResponseEntity.ok(ApiResponse.success());
    }

    // 사용자 카테고리 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserCategoryResponse>>> getUserCategories(@RequestParam Long userId) {
        List<UserCategoryResponse> categories = categoryFacadeService.getUserCategories(userId);
        return ResponseEntity.ok(ApiResponse.success(categories));
    }
} 