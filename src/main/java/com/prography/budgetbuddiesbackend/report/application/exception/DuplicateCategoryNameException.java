package com.prography.budgetbuddiesbackend.report.application.exception;

import static com.prography.budgetbuddiesbackend.common.response.ResultCode.*;


import com.prography.budgetbuddiesbackend.common.response.error.BusinessException;
import com.prography.budgetbuddiesbackend.common.response.error.ErrorDetail;

public class DuplicateCategoryNameException extends BusinessException {
	public DuplicateCategoryNameException() {
		super(DUPLICATE, new ErrorDetail("categoryName", "중복된 카테고리 명이 존재합니다."));
	}
}
