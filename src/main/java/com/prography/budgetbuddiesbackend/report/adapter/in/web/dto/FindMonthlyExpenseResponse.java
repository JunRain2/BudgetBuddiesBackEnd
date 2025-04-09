package com.prography.budgetbuddiesbackend.report.adapter.in.web.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.prography.budgetbuddiesbackend.report.application.port.in.expense.FindMonthlyExpenseResult;

import lombok.Getter;

@Getter
public class FindMonthlyExpenseResponse {
	private final Map<LocalDate, ExpenseResponse> expenseList;

	public FindMonthlyExpenseResponse(List<FindMonthlyExpenseResult> results) {
		this.expenseList = results.stream().collect(
			Collectors.toMap(
				FindMonthlyExpenseResult::expenseAt,
				e -> new ExpenseResponse(e.expenseId(), e.description(), e.amount())
			)
		);
	}

	private record ExpenseResponse(
		Long id,
		String description,
		int amount
	) {
	}
}
