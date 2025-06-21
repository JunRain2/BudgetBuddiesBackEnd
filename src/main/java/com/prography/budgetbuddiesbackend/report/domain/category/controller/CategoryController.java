package com.prography.budgetbuddiesbackend.report.domain.category.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prography.budgetbuddiesbackend.common.annotation.CurrentUserId;
import com.prography.budgetbuddiesbackend.common.response.ApiResponse;
import com.prography.budgetbuddiesbackend.report.domain.category.controller.dto.request.RegisterCategoryRequest;
import com.prography.budgetbuddiesbackend.report.domain.category.controller.dto.response.UserCategoryResponse;
import com.prography.budgetbuddiesbackend.report.domain.category.service.CategoryFacadeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryFacadeService categoryFacadeService;

	// 카테고리 생성
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> registerCategory(@RequestBody @Valid RegisterCategoryRequest request,
		@CurrentUserId @RequestParam Long userId) {
		categoryFacadeService.registerCategory(request, userId);
		return ResponseEntity.ok(ApiResponse.success());
	}

	// 카테고리 삭제
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long categoryId,
		@CurrentUserId @RequestParam Long userId) {
		categoryFacadeService.deleteCategory(categoryId, userId);
		return ResponseEntity.ok(ApiResponse.success());
	}

	// 사용자 카테고리 전체 조회
	@GetMapping
	public ResponseEntity<ApiResponse<List<UserCategoryResponse>>> getUserCategories(
		@CurrentUserId @RequestParam Long userId) {
		List<UserCategoryResponse> result = categoryFacadeService.getUserCategories(userId);
		return ResponseEntity.ok(ApiResponse.success(result));
	}
} 