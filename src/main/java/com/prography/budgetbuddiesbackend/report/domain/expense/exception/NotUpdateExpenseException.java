package com.prography.budgetbuddiesbackend.report.domain.expense.exception;

import com.prography.budgetbuddiesbackend.common.response.ResultCode;
import com.prography.budgetbuddiesbackend.common.response.error.BusinessException;

public class NotUpdateExpenseException extends BusinessException {
	public NotUpdateExpenseException() {
		super(ResultCode.INVALID_INPUT);
	}
}
