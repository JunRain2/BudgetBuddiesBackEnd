package com.prography.budgetbuddiesbackend.report.domain.expense.service;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;
import com.prography.budgetbuddiesbackend.report.domain.expense.exception.NotFoundExpenseException;
import com.prography.budgetbuddiesbackend.report.domain.expense.repository.ExpenseRepository;
import com.prography.budgetbuddiesbackend.report.domain.expense.repository.dto.SumAmountGroupByCategoryResponse;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
	private final ExpenseRepository expenseRepository;

	public Expense save(Expense expense) {
		return expenseRepository.save(expense);
	}

	// TODO 현재까지는 Category에서만 필요로 하기에 ExpenseService에서 제외
	// 해당 메서드를 호출할 때, Cateogry를 변경할 권한이 있는지는 호출하는 쪽에서 검증한다.
	public void reassignCategory(Category deletedCategory, Category uncategorized) {
		expenseRepository.clearCategoryReference(deletedCategory, uncategorized);
	}

	public void delete(Expense expense) {
		expenseRepository.delete(expense);
	}

	public Expense findById(Long id) {
		return expenseRepository.findById(id).orElseThrow(NotFoundExpenseException::new);
	}

	// TODO 리팩토링
	public Map<Long, Integer> getCategorySpendingByUserAndMonth(User user, YearMonth yearMonth) {
		List<SumAmountGroupByCategoryResponse> list = expenseRepository.findSumAmountGroupedByCategoryIdAndUserIdAndYearMonth(
			user.getId(),
			yearMonth.atDay(1),
			yearMonth.atEndOfMonth()
		);
		Map<Long, Integer> map = new HashMap<>();
		for (SumAmountGroupByCategoryResponse dto : list) {
			map.put(dto.categoryId(), dto.spendingMoney());
		}
		return map;
	}
} 