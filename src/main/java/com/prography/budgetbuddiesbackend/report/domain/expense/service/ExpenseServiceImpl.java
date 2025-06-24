package com.prography.budgetbuddiesbackend.report.domain.expense.service;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryId;
import com.prography.budgetbuddiesbackend.report.domain.category.service.CategoryExpenseService;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service.ConsumptionGoalExpenseService;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.ExpenseId;
import com.prography.budgetbuddiesbackend.report.domain.expense.exception.NotFoundExpenseException;
import com.prography.budgetbuddiesbackend.report.domain.expense.repository.ExpenseRepository;
import com.prography.budgetbuddiesbackend.report.domain.expense.repository.dto.SumAmountGroupByCategoryResult;
import com.prography.budgetbuddiesbackend.user.entity.UserId;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService, CategoryExpenseService,
    ConsumptionGoalExpenseService {

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
    public Expense findById(ExpenseId id) {
        return expenseRepository.findById(id).orElseThrow(NotFoundExpenseException::new);
    }

    @Override
    public void reassignCategory(Category deletedCategory, Category uncategorized) {
        expenseRepository.clearCategoryReference(deletedCategory, uncategorized);
    }

    @Override
    public Map<CategoryId, Integer> getTotalSpentByUserCategory(UserId userId,
        YearMonth yearMonth) {
        List<SumAmountGroupByCategoryResult> results = expenseRepository.findSumAmountGroupedByCategoryIdAndUserIdAndYearMonth(
            userId, yearMonth.atDay(1), yearMonth.atEndOfMonth());

        return results.stream().collect(Collectors.toMap(SumAmountGroupByCategoryResult::categoryId,
            SumAmountGroupByCategoryResult::spendingMoney));
    }
} 