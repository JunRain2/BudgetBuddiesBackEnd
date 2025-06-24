package com.prography.budgetbuddiesbackend.report.domain.expense.service;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;
import com.prography.budgetbuddiesbackend.report.domain.expense.service.command.RegisterExpenseCommand;
import com.prography.budgetbuddiesbackend.user.entity.UserId;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    /**
     * 지출 등록 요청을 엔티티로 변환합니다.
     *
     * @param command  지출 등록 요청
     * @param category 카테고리
     * @param userId   사용자 ID
     * @return 생성된 지출 엔티티
     */
    public Expense requestToEntity(RegisterExpenseCommand command, Category category,
        UserId userId) {
        return Expense.of(userId, category, command.amount(), command.description(),
            command.expenseAt());
    }
}
