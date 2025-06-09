package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.report.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ConsumptionGoalService {
	private final ConsumptionGoalDomainService consumptionGoalService;
	private final ConsumptionGoalMapper mapper;

	private final UserService userService;
}