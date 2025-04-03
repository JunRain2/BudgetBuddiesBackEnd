package com.prography.budgetbuddiesbackend.report.domain.exception;

import org.springframework.http.HttpStatus;

import com.prography.budgetbuddiesbackend.common.exception.BusinessException;

public class DuplicateCategoryNameException extends BusinessException {
	public DuplicateCategoryNameException(String message, HttpStatus status) {
		super("중복된 카테고리명이 존재합니다.", HttpStatus.CONFLICT);
	}
}
