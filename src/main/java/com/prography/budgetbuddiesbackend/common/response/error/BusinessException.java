package com.prography.budgetbuddiesbackend.common.response.error;

import com.prography.budgetbuddiesbackend.common.response.ResultCode;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
	private final ResultCode code;

	public BusinessException(ResultCode code) {
		super(code.getMessage());
		this.code = code;
	}
}
