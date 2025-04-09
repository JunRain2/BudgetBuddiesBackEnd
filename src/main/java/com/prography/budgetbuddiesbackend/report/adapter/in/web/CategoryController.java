package com.prography.budgetbuddiesbackend.report.adapter.in.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prography.budgetbuddiesbackend.report.adapter.in.web.dto.RegisterCategoryRequest;
import com.prography.budgetbuddiesbackend.report.adapter.in.web.dto.UserCategoryListResponse;
import com.prography.budgetbuddiesbackend.report.application.port.in.category.CategoryUseCase;
import com.prography.budgetbuddiesbackend.report.application.port.in.category.DeleteCategoryCommand;
import com.prography.budgetbuddiesbackend.report.application.port.in.category.FindUserCategoryResult;
import com.prography.budgetbuddiesbackend.report.application.port.in.category.RegisterCategoryCommand;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
class CategoryController {
	private final CategoryUseCase categoryUseCase;

	@GetMapping("/{userId}")
	ResponseEntity<UserCategoryListResponse> findUserCategoryList(@PathVariable("userId") Long userId) {
		List<FindUserCategoryResult> results = categoryUseCase.findUserCategoryList(userId);

		return ResponseEntity.ok(new UserCategoryListResponse(results));
	}

	@PostMapping("/{userId}")
	ResponseEntity<HttpStatus> registerCategory(@PathVariable Long userId, RegisterCategoryRequest request) {
		RegisterCategoryCommand command = new RegisterCategoryCommand(userId, request.getCategoryName());
		categoryUseCase.registerCategory(command);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/{userId}/{categoryId}")
	ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long userId, @PathVariable Long categoryId) {
		DeleteCategoryCommand command = new DeleteCategoryCommand(userId, categoryId);
		categoryUseCase.deleteCategory(command);

		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
