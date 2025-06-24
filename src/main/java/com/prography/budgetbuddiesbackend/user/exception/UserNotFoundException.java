package com.prography.budgetbuddiesbackend.user.exception;

import com.prography.budgetbuddiesbackend.common.response.ResultCode;
import com.prography.budgetbuddiesbackend.common.response.error.BusinessException;
import com.prography.budgetbuddiesbackend.common.response.error.ErrorDetail;
 
public class UserNotFoundException extends BusinessException {
	public UserNotFoundException() {
		super(ResultCode.NOT_FOUND);
	}
} 