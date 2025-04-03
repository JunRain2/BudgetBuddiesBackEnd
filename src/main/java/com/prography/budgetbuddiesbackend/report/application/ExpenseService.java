package com.prography.budgetbuddiesbackend.report.application;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prography.budgetbuddiesbackend.common.vo.Money;
import com.prography.budgetbuddiesbackend.report.application.port.in.expense.ExpenseUseCase;
import com.prography.budgetbuddiesbackend.report.application.port.in.expense.FindDetailExpenseResult;
import com.prography.budgetbuddiesbackend.report.application.port.in.expense.FindMonthlyExpenseResult;
import com.prography.budgetbuddiesbackend.report.application.port.in.expense.RegisterExpenseCommand;
import com.prography.budgetbuddiesbackend.report.application.port.in.expense.UpdateExpenseCommand;
import com.prography.budgetbuddiesbackend.report.application.port.out.expense.UpdateExpensePort;
import com.prography.budgetbuddiesbackend.report.application.port.out.category.FindCategoryPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.expense.FindExpensePort;
import com.prography.budgetbuddiesbackend.report.application.port.out.expense.RegisterExpensePort;
import com.prography.budgetbuddiesbackend.report.domain.Category;
import com.prography.budgetbuddiesbackend.report.domain.Expense;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class ExpenseService implements ExpenseUseCase {

	private final RegisterExpensePort registerExpensePort;
	private final FindExpensePort findExpensePort;
	private final UpdateExpensePort updateExpensePort;
	private final FindCategoryPort findCategoryPort;

	@Override
	public void registerExpense(RegisterExpenseCommand command) {
		Expense expense = new Expense(
			null,
			command.categoryId(),
			command.description(),
			new Money(command.amount()),
			command.expenseAt());

		registerExpensePort.registerExpense(command.userId(), expense);
	}

	@Override
	public List<FindMonthlyExpenseResult> findMonthlyExpenseList(Long userId, LocalDate expenseMonth) {
		List<Expense> expenseList = findExpensePort.findMonthlyExpense(userId, expenseMonth);

		return expenseList.stream().map(
			e -> new FindMonthlyExpenseResult(
				e.getExpenseId(),
				e.getExpenseAt(),
				e.getDescription(),
				e.getSpendingMoney().value())
		).toList();
	}

	@Override
	public FindDetailExpenseResult findDetailExpenseResult(Long expenseId) {
		Expense expense = findExpensePort.findExpense(expenseId);
		Category category = findCategoryPort.findCategory(expense.getCategoryId());

		return new FindDetailExpenseResult(
			expense.getExpenseId(),
			category.getName(),
			expense.getDescription(),
			expense.getSpendingMoney().value(),
			expense.getExpenseAt());
	}

	@Override
	public void updateExpense(UpdateExpenseCommand command) {
		Expense expense = findExpensePort.findExpense(command.expenseId());
		expense.changeCategory(command.categoryId());
		expense.modifyExpenseAt(command.expenseAt());

		updateExpensePort.updateExpense(expense);
	}
}
