package com.prography.budgetbuddiesbackend.report.domain;

import java.time.LocalDate;

import com.prography.budgetbuddiesbackend.common.vo.Money;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Expense {
	private Long expenseId;
	private Long categoryId;
	private String description;
	private Money spendingMoney;
	private LocalDate expenseAt;

	public void changeCategory(Long categoryId) {
		this.categoryId = categoryId;
	}

	public void modifyExpenseAt(LocalDate localDate) {
		this.expenseAt = localDate;
	}
}
