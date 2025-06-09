package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.exception;

import static com.prography.budgetbuddiesbackend.common.response.ResultCode.*;

import com.prography.budgetbuddiesbackend.common.response.error.BusinessException;

public class NotFoundConsumptionGoalException extends BusinessException {
	public NotFoundConsumptionGoalException() {
		super(NOT_FOUND);
	}
}
