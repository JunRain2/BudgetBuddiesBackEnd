package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.prography.budgetbuddiesbackend.common.vo.Money;
import com.prography.budgetbuddiesbackend.report.domain.Expense;

@Component
class ExpenseMapper {
	Expense entityToExpense(ExpenseEntity expenseEntity) {
		return new Expense(
			expenseEntity.getId(),
			expenseEntity.getCategoryId(),
			expenseEntity.getDescription(),
			new Money(expenseEntity.getAmount()),
			expenseEntity.getExpenseAt()
		);
	}

	ExpenseEntity expenseToEntity(Long userId, Expense expense) {
		return new ExpenseEntity(
			expense.getExpenseId(),
			expense.getSpendingMoney().value(),
			expense.getDescription(),
			expense.getExpenseAt(),
			userId,
			expense.getCategoryId()
		);
	}
}
