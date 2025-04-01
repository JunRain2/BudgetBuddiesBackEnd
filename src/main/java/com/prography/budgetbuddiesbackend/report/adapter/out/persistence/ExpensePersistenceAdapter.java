package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.prography.budgetbuddiesbackend.report.adapter.out.exception.NotFoundExpenseException;
import com.prography.budgetbuddiesbackend.report.application.UpdateExpensePort;
import com.prography.budgetbuddiesbackend.report.application.port.out.expense.FindExpensePort;
import com.prography.budgetbuddiesbackend.report.application.port.out.expense.RegisterExpensePort;
import com.prography.budgetbuddiesbackend.report.domain.Expense;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ExpensePersistenceAdapter implements FindExpensePort, RegisterExpensePort, UpdateExpensePort {

	private final ExpenseRepository expenseRepository;
	private final ExpenseMapper mapper;

	@Override
	public void updateExpense(Expense expense) {
		ExpenseEntity expenseEntity = expenseRepository.findById(expense.getExpenseId())
			.orElseThrow(NotFoundExpenseException::new);

		expenseEntity.updateExpenseAtAndCategoryId(expense.getExpenseAt(), expense.getCategoryId());
		expenseRepository.save(expenseEntity);
	}

	@Override
	public List<Expense> findMonthlyExpense(Long userId, LocalDate expenseMonth) {
		List<ExpenseEntity> expenseEntityList = expenseRepository.findByUserIdAndExpenseMonth(
			userId,
			expenseMonth.getYear(),
			expenseMonth.getMonthValue());

		return expenseEntityList.stream().map(mapper::entityToExpense).toList();
	}

	@Override
	public Expense findExpense(Long expenseId) {
		ExpenseEntity expenseEntity = expenseRepository.findById(expenseId).orElseThrow(NotFoundExpenseException::new);

		return mapper.entityToExpense(expenseEntity);
	}

	@Override
	public void registerExpense(Long userId, Expense expense) {
		ExpenseEntity expenseEntity = mapper.expenseToEntity(userId, expense);
		expenseRepository.save(expenseEntity);
	}
}
