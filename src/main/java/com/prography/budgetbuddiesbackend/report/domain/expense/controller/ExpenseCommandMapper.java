package com.prography.budgetbuddiesbackend.report.domain.expense.controller;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryId;
import com.prography.budgetbuddiesbackend.report.domain.expense.controller.dto.request.RegisterExpenseRequest;
import com.prography.budgetbuddiesbackend.report.domain.expense.controller.dto.request.UpdateExpenseRequest;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.ExpenseId;
import com.prography.budgetbuddiesbackend.report.domain.expense.service.command.RegisterExpenseCommand;
import com.prography.budgetbuddiesbackend.report.domain.expense.service.command.UpdateExpenseCommand;
import org.springframework.stereotype.Component;

@Component
public class ExpenseCommandMapper {

    UpdateExpenseCommand requestToUpdateExpenseCommand(UpdateExpenseRequest request) {

        ExpenseId expenseKey = ExpenseId.of(request.expenseId());
        CategoryId categoryKey = CategoryId.of(request.categoryId());

        return new UpdateExpenseCommand(expenseKey, categoryKey, request.expenseAt());
    }

    RegisterExpenseCommand requestToRegisterExpenseCommand(RegisterExpenseRequest request) {

        CategoryId categoryKey = CategoryId.of(request.categoryId());

        return new RegisterExpenseCommand(categoryKey, request.amount(), request.description(),
            request.expenseAt());
    }
}
