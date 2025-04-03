package com.prography.budgetbuddiesbackend.report.adapter.out.exception;

import org.springframework.http.HttpStatus;

import com.prography.budgetbuddiesbackend.common.exception.BusinessException;

public class NotFoundExpenseException extends BusinessException {
	public NotFoundExpenseException() {
		super("존재하지 않는 소비내역입니다.", HttpStatus.NOT_FOUND);
	}
}
