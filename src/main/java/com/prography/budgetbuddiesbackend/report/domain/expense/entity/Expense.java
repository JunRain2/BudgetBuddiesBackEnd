package com.prography.budgetbuddiesbackend.report.domain.expense.entity;

import java.time.LocalDate;

import com.prography.budgetbuddiesbackend.common.entity.BaseEntity;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.expense.exception.NotRegisterExpenseException;
import com.prography.budgetbuddiesbackend.report.domain.expense.exception.NotUpdateExpenseException;
import com.prography.budgetbuddiesbackend.user.entity.UserId;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class Expense extends BaseEntity {

	@EmbeddedId
	private ExpenseId expenseId;

	@Embedded
	@NotNull
	@AttributeOverride(name = "id", column = @Column(name = "user_id", nullable = false))
	private UserId userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

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

	private Expense(ExpenseId expenseId, UserId userId, Category category, Integer amount, String description,
		LocalDate expenseAt) {
		this.expenseId = expenseId;
		this.userId = userId;
		this.category = category;
		this.amount = amount;
		this.description = description;
		this.expenseAt = expenseAt;
	}

	public static Expense of(UserId userId, Category category, Integer amount, String description, LocalDate expenseAt) {
		LocalDate now = LocalDate.now();
		if (now.isBefore(expenseAt) || amount <= 0) {
			throw new NotRegisterExpenseException();
		}

		return new Expense(ExpenseId.generate(), userId, category, amount, description, expenseAt);
	}

	public void update(Category category, LocalDate expenseAt) {
		this.category = category;
		this.expenseAt = expenseAt;
	}

	public void validateOwner(UserId userId) {
		if (!this.userId.equals(userId)) {
			throw new NotUpdateExpenseException();
		}
	}

	public void validateModifiable(UserId userId, LocalDate expenseAt) {
		validateOwner(userId);

		LocalDate now = LocalDate.now();
		if (now.isBefore(expenseAt)) {
			throw new NotUpdateExpenseException();
		}
	}
}