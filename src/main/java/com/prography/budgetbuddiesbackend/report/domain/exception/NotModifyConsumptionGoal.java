package com.prography.budgetbuddiesbackend.report.domain.exception;

import org.springframework.http.HttpStatus;

import com.prography.budgetbuddiesbackend.common.exception.BusinessException;

public class NotModifyConsumptionGoal extends BusinessException {
	public NotModifyConsumptionGoal() {
		super("변경 불가능한 소비목표입니다.", HttpStatus.NOT_MODIFIED);
	}
}
