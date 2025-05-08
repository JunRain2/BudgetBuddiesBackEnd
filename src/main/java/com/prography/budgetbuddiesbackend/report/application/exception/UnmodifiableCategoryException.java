package com.prography.budgetbuddiesbackend.report.application.exception;

import com.prography.budgetbuddiesbackend.common.response.ResultCode;
import com.prography.budgetbuddiesbackend.common.response.error.BusinessException;
import com.prography.budgetbuddiesbackend.common.response.error.ErrorDetail;

public class UnmodifiableCategoryException extends BusinessException {
	public UnmodifiableCategoryException() {
		super(ResultCode.FORBIDDEN, new ErrorDetail("category", "사용자가 수정이 불가능한 카테고리입니다."));
	}
}
