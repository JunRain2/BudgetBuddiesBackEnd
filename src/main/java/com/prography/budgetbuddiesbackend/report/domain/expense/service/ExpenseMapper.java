package com.prography.budgetbuddiesbackend.report.domain.expense.service;

import org.springframework.stereotype.Component;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.expense.controller.dto.request.RegisterExpenseRequest;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;

@Component
public class ExpenseMapper {
	/**
	 * 지출 등록 요청을 엔티티로 변환합니다.
	 * @param request 지출 등록 요청
	 * @param category 카테고리
	 * @param user 사용자
	 * @return 생성된 지출 엔티티
	 */
	public Expense requestToEntity(RegisterExpenseRequest request, Category category, User user) {
		return Expense.of(user, category, request.amount(), request.description(), request.expenseAt());
	}
}
