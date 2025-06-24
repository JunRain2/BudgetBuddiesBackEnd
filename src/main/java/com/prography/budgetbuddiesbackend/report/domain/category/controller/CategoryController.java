package com.prography.budgetbuddiesbackend.report.domain.category.controller;

import com.prography.budgetbuddiesbackend.common.annotation.CurrentUserId;
import com.prography.budgetbuddiesbackend.common.response.ApiResponse;
import com.prography.budgetbuddiesbackend.report.domain.category.controller.dto.request.RegisterCategoryRequest;
import com.prography.budgetbuddiesbackend.report.domain.category.controller.dto.response.UserCategoryResponse;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryId;
import com.prography.budgetbuddiesbackend.report.domain.category.service.CategoryFacadeService;
import com.prography.budgetbuddiesbackend.user.entity.UserId;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryFacadeService categoryFacadeService;

  // 카테고리 생성
  @PostMapping
  public ResponseEntity<ApiResponse<Void>> registerCategory(
      @RequestBody @Valid RegisterCategoryRequest request,
      @CurrentUserId @RequestParam UUID userId) {

    UserId userKey = UserId.of(userId);

    categoryFacadeService.registerCategory(request, userKey);
    return ResponseEntity.ok(ApiResponse.success());
  }

  // 카테고리 삭제
  @DeleteMapping("/{categoryId}")
  public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable UUID categoryId,
      @CurrentUserId @RequestParam UUID userId) {

    CategoryId categoryKey = CategoryId.of(categoryId);
    UserId userKey = UserId.of(userId);

    categoryFacadeService.deleteCategory(categoryKey, userKey);
    return ResponseEntity.ok(ApiResponse.success());
  }

  // 사용자 카테고리 전체 조회
  @GetMapping
  public ResponseEntity<ApiResponse<List<UserCategoryResponse>>> getUserCategories(
      @CurrentUserId @RequestParam UUID userId) {

    UserId userKey = UserId.of(userId);

    List<UserCategoryResponse> result = categoryFacadeService.getUserCategories(userKey);

    return ResponseEntity.ok(ApiResponse.success(result));
  }
} 