package com.prography.budgetbuddiesbackend.report.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Expense {
	private Long expenseId;
	private String description;
	private Category category;
	private Money spendingMoney;
	private LocalDate expenseAt;

	public void changeCategory(Category category) {
		this.category = category;
	}

	public void modifyExpenseAt(LocalDate localDate) {
		this.expenseAt = localDate;
	}
}
