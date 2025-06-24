package com.prography.budgetbuddiesbackend.report.domain.expense.service;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.service.CategoryService;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.ExpenseId;
import com.prography.budgetbuddiesbackend.report.domain.expense.service.command.RegisterExpenseCommand;
import com.prography.budgetbuddiesbackend.report.domain.expense.service.command.UpdateExpenseCommand;
import com.prography.budgetbuddiesbackend.user.entity.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpenseFacadeService implements ExpenseUseCase {

    private final ExpenseService expenseService;
    private final ExpenseMapper mapper;

    private final CategoryService categoryService;

    @Override
    public void registerExpense(RegisterExpenseCommand request, UserId userId) {
        Category category = categoryService.findById(request.categoryId());
        Expense expense = mapper.requestToEntity(request, category, userId);

        expenseService.save(expense);
    }

    @Override
    public void updateExpense(UpdateExpenseCommand request, UserId userId) {
        Expense expense = expenseService.findById(request.expenseId());
        expense.validateModifiable(userId, request.expenseAt());

        Category category = categoryService.findById(request.categoryId());
        expense.update(category, request.expenseAt());

        expenseService.save(expense);
    }

    @Override
    public void deleteExpense(ExpenseId expenseId, UserId userId) {
        Expense expense = expenseService.findById(expenseId);
        expense.validateOwner(userId);

        expenseService.delete(expense);
    }
}
