package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.exception;

import com.prography.budgetbuddiesbackend.common.response.ResultCode;
import com.prography.budgetbuddiesbackend.common.response.error.BusinessException;

public class NotUpdateConsumptionGoalException extends BusinessException {
	public NotUpdateConsumptionGoalException() {
		super(ResultCode.INVALID_INPUT);
	}
}
