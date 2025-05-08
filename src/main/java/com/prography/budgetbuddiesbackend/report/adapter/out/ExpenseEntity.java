package com.prography.budgetbuddiesbackend.report.adapter.out;

import java.time.LocalDate;

import com.prography.budgetbuddiesbackend.common.entity.BaseEntity;
import com.prography.budgetbuddiesbackend.report.adapter.out.category.CategoryEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "expense", schema = "budgetbuddies")
public class ExpenseEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private CategoryEntity category;

	@NotNull
	@Column(name = "amount", nullable = false)
	private Integer amount;

	@Size(max = 100)
	@NotNull
	@Column(name = "description", nullable = false, length = 100)
	private String description;

	@NotNull
	@Column(name = "expense_at", nullable = false)
	private LocalDate expenseAt;

	private ExpenseEntity(UserEntity user, CategoryEntity category, Integer amount, String description,
		LocalDate expenseAt) {
		this.user = user;
		this.category = category;
		this.amount = amount;
		this.description = description;
		this.expenseAt = expenseAt;
	}

	public static ExpenseEntity of(UserEntity user, CategoryEntity category, Integer amount, String description,
		LocalDate expenseAt) {
		return new ExpenseEntity(user, category, amount, description, expenseAt);
	}
}