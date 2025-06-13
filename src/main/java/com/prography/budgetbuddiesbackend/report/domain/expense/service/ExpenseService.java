package com.prography.budgetbuddiesbackend.report.domain.expense.service;

import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;

public interface ExpenseService {
	/**
	 * 지출을 저장합니다.
	 * @param expense 저장할 지출
	 * @return 저장된 지출
	 */
	Expense save(Expense expense);

	/**
	 * 지출을 삭제합니다.
	 * @param expense 삭제할 지출
	 */
	void delete(Expense expense);

	/**
	 * ID로 지출을 조회합니다.
	 * @param id 조회할 지출 ID
	 * @return 조회된 지출
	 * @throws NotFoundExpenseException 지출이 존재하지 않는 경우
	 */
	Expense findById(Long id);
}

