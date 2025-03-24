package com.prography.budgetbuddiesbackend.expense.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Expense {
	private Long expenseId;
	private Long userId;
	private Long categoryId;
	private Integer amount;
	private String description;
	private LocalDateTime registerAt;

	public static Expense of(Long expenseId, Long userId, Long categoryId, Integer amount, String description,
		LocalDateTime registerAt) {

		return new Expense(expenseId, userId, categoryId, amount, description, registerAt);
	}
}
