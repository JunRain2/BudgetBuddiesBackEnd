package com.prography.budgetbuddiesbackend.common.response.error;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.prography.budgetbuddiesbackend.common.response.ResultCode;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
	private final ResultCode code;
	private final List<ErrorDetail> errors;

	public BusinessException(ResultCode code) {
		super(code.getMessage());
		this.code = code;
		this.errors = new ArrayList<>();
	}

	public BusinessException(ResultCode code, ErrorDetail... errors) {
		super(code.getMessage());
		this.code = code;
		this.errors = List.of(errors);
	}
}
