package com.prography.budgetbuddiesbackend.report.domain.expense.exception;

import com.prography.budgetbuddiesbackend.common.response.ResultCode;
import com.prography.budgetbuddiesbackend.common.response.error.BusinessException;

public class NotRegisterExpenseException extends BusinessException {
	public NotRegisterExpenseException() {
		super(ResultCode.INVALID_INPUT);
	}
}
