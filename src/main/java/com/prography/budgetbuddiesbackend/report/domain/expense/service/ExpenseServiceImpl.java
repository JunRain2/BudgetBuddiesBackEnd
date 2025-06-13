package com.prography.budgetbuddiesbackend.report.domain.expense.service;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.service.CategoryExpenseService;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service.ConsumptionGoalExpenseService;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;
import com.prography.budgetbuddiesbackend.report.domain.expense.exception.NotFoundExpenseException;
import com.prography.budgetbuddiesbackend.report.domain.expense.repository.ExpenseRepository;
import com.prography.budgetbuddiesbackend.report.domain.expense.repository.dto.SumAmountGroupByCategoryResult;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService, CategoryExpenseService, ConsumptionGoalExpenseService {
	private final ExpenseRepository expenseRepository;

	@Override
	public Expense save(Expense expense) {
		return expenseRepository.save(expense);
	}

	@Override
	public void delete(Expense expense) {
		expenseRepository.delete(expense);
	}

	@Override
	public Expense findById(Long id) {
		return expenseRepository.findById(id).orElseThrow(NotFoundExpenseException::new);
	}

	// 해당 메서드를 호출할 때, Cateogry를 변경할 권한이 있는지는 호출하는 쪽에서 검증한다.
	@Override
	public void reassignCategory(Category deletedCategory, Category uncategorized) {
		expenseRepository.clearCategoryReference(deletedCategory, uncategorized);
	}

	@Override
	public Map<Long, Integer> getTotalSpentByUserCategory(User user, YearMonth yearMonth) {
		List<SumAmountGroupByCategoryResult> results = expenseRepository.findSumAmountGroupedByCategoryIdAndUserIdAndYearMonth(
			user.getId(),
			yearMonth.atDay(1),
			yearMonth.atEndOfMonth()
		);

		return results.stream()
			.collect(
				Collectors.toMap(
					SumAmountGroupByCategoryResult::categoryId,
					SumAmountGroupByCategoryResult::spendingMoney
				)
			);
	}
} 