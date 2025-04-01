package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import java.time.LocalDate;

import com.prography.budgetbuddiesbackend.common.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "expense")
@Getter
class ExpenseEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int amount;

	private String description;

	private LocalDate expenseAt;

	private Long userId;

	private Long categoryId;

	void updateExpenseAtAndCategoryId(LocalDate expenseAt, Long categoryId) {
		this.expenseAt = expenseAt;
		this.categoryId = categoryId;
	}
}
