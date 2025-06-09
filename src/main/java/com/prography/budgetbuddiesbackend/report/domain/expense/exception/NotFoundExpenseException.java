package com.prography.budgetbuddiesbackend.report.domain.expense.exception;

import com.prography.budgetbuddiesbackend.common.response.ResultCode;
import com.prography.budgetbuddiesbackend.common.response.error.BusinessException;

public class NotFoundExpenseException extends BusinessException {
	public NotFoundExpenseException() {
		super(ResultCode.NOT_FOUND);
	}
}
