package com.prography.budgetbuddiesbackend.report.domain.user.exception;

import com.prography.budgetbuddiesbackend.common.response.ResultCode;
import com.prography.budgetbuddiesbackend.common.response.error.BusinessException;
import com.prography.budgetbuddiesbackend.common.response.error.ErrorDetail;
 
public class UserNotFoundException extends BusinessException {
	public UserNotFoundException(Long userId) {
		super(ResultCode.NOT_FOUND, new ErrorDetail("userId", "존재하지 않는 사용자입니다."));
	}
} 