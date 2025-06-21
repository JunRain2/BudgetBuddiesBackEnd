package com.prography.budgetbuddiesbackend.report.domain.expense.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prography.budgetbuddiesbackend.common.annotation.CurrentUserId;
import com.prography.budgetbuddiesbackend.common.response.ApiResponse;
import com.prography.budgetbuddiesbackend.report.domain.expense.controller.dto.request.RegisterExpenseRequest;
import com.prography.budgetbuddiesbackend.report.domain.expense.controller.dto.request.UpdateExpenseRequest;
import com.prography.budgetbuddiesbackend.report.domain.expense.service.ExpenseUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
	private final ExpenseUseCase expenseFacadeService;

	// 지출 등록
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> registerExpense(@RequestBody @Valid RegisterExpenseRequest request,
		@CurrentUserId @RequestParam Long userId) {
		expenseFacadeService.registerExpense(request, userId);
		return ResponseEntity.ok(ApiResponse.success());
	}

	// 지출 수정
	@PatchMapping
	public ResponseEntity<ApiResponse<Void>> updateExpense(
		@RequestBody @Valid UpdateExpenseRequest request,
		@CurrentUserId @RequestParam Long userId) {

		expenseFacadeService.updateExpense(request, userId);
		return ResponseEntity.ok(ApiResponse.success());
	}

	// 지출 삭제
	@DeleteMapping("/{expenseId}")
	public ResponseEntity<ApiResponse<Void>> deleteExpense(@PathVariable Long expenseId,
		@CurrentUserId @RequestParam Long userId) {
		expenseFacadeService.deleteExpense(expenseId, userId);
		return ResponseEntity.ok(ApiResponse.success());
	}
} 