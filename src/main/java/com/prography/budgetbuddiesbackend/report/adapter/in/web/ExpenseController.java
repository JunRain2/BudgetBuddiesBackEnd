package com.prography.budgetbuddiesbackend.report.adapter.in.web;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prography.budgetbuddiesbackend.report.adapter.in.FindExpenseResponse;
import com.prography.budgetbuddiesbackend.report.adapter.in.FindMonthlyExpenseResponse;
import com.prography.budgetbuddiesbackend.report.adapter.in.RegisterExpenseRequest;
import com.prography.budgetbuddiesbackend.report.adapter.in.UpdateExpenseRequest;
import com.prography.budgetbuddiesbackend.report.application.port.in.expense.ExpenseUseCase;
import com.prography.budgetbuddiesbackend.report.application.port.in.expense.FindDetailExpenseResult;
import com.prography.budgetbuddiesbackend.report.application.port.in.expense.FindMonthlyExpenseResult;
import com.prography.budgetbuddiesbackend.report.application.port.in.expense.RegisterExpenseCommand;
import com.prography.budgetbuddiesbackend.report.application.port.in.expense.UpdateExpenseCommand;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/expense")
@RequiredArgsConstructor
public class ExpenseController {

	private final ExpenseUseCase expenseUseCase;

	@PostMapping("/{userId}")
	ResponseEntity<HttpStatus> registerExpense(
		@PathVariable Long userId,
		@RequestBody RegisterExpenseRequest request
	) {
		RegisterExpenseCommand command = new RegisterExpenseCommand(
			request.amount(),
			userId,
			request.categoryId(),
			request.description(),
			request.expenseAt());

		expenseUseCase.registerExpense(command);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/{userId}/{yearMonth}")
	ResponseEntity<FindMonthlyExpenseResponse> findMonthlyExpense(
		@PathVariable Long userId,
		@PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth
	) {
		LocalDate thisMonth = yearMonth.atDay(1);
		List<FindMonthlyExpenseResult> results = expenseUseCase.findMonthlyExpenseList(userId, thisMonth);

		FindMonthlyExpenseResponse response = new FindMonthlyExpenseResponse(results);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{expenseId}")
	ResponseEntity<FindExpenseResponse> findExpense(@PathVariable Long expenseId) {
		FindDetailExpenseResult result = expenseUseCase.findDetailExpenseResult(expenseId);

		FindExpenseResponse response = new FindExpenseResponse(
			result.expenseId(),
			result.categoryName(),
			result.description(),
			result.amount(),
			result.expenseAt());

		return ResponseEntity.ok(response);
	}

	@PostMapping("/{expenseId}")
	ResponseEntity<HttpStatus> updateExpense(@PathVariable Long expenseId, @RequestBody UpdateExpenseRequest request) {
		UpdateExpenseCommand command = new UpdateExpenseCommand(expenseId, request.categoryId(), request.expenseAt());
		expenseUseCase.updateExpense(command);

		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
