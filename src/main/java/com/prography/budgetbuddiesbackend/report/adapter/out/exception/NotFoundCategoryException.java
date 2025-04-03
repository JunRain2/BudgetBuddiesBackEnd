package com.prography.budgetbuddiesbackend.report.adapter.out.exception;

import org.springframework.http.HttpStatus;

import com.prography.budgetbuddiesbackend.common.exception.BusinessException;

public class NotFoundCategoryException extends BusinessException {
	public NotFoundCategoryException() {
		super("존재하지 않는 카테고리입니다.", HttpStatus.NOT_FOUND);
	}
}
