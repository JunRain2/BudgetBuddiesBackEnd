package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.controller;

import java.time.YearMonth;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prography.budgetbuddiesbackend.common.annotation.CurrentUserId;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.dto.UserConsumptionGoalResponse;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service.ConsumptionGoalFacadeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/consumption-goals")
@RequiredArgsConstructor
public class ConsumptionGoalController {
	private final ConsumptionGoalFacadeService consumptionGoalService;

	@GetMapping
	public List<UserConsumptionGoalResponse> getUserConsumptionGoalsByMonth(
		@RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth,
		@CurrentUserId @RequestParam Long userId
	) {
		return consumptionGoalService.getUserConsumptionGoalsByMonth(userId, yearMonth);
	}
} 