package com.prography.budgetbuddiesbackend.report.domain.category.exception;

import com.prography.budgetbuddiesbackend.common.response.ResultCode;
import com.prography.budgetbuddiesbackend.common.response.error.BusinessException;
import com.prography.budgetbuddiesbackend.common.response.error.ErrorDetail;

public class NotFoundCategoryException extends BusinessException {
	public NotFoundCategoryException() {
		super(ResultCode.NOT_FOUND, new ErrorDetail("consumptionGoalId", "존재하지 않는 카테고리입니다."));
	}
}
