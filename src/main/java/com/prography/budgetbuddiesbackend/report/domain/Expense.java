package com.prography.budgetbuddiesbackend.report.domain;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Expense {
	private Long id;
	private Long categoryId;
	private Integer amount;
	private String description;
	private LocalDate expenseAt;

	public static Expense of(Long id, Long categoryId, Integer amount, String description, LocalDate expenseAt) {
		return new Expense(id, categoryId, amount, description, expenseAt);
	}
}
