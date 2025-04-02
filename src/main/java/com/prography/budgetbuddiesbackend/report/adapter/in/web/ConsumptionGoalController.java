package com.prography.budgetbuddiesbackend.report.adapter.in.web;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prography.budgetbuddiesbackend.report.adapter.in.UserMonthlyConsumptionGoalListResponse;
import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.CommandConsumptionGoalUseCase;
import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.QueryConsumptionGoalResult;
import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.QueryConsumptionGoalUseCase;
import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.UpdateCapsCommand;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/consumption-goal")
@RequiredArgsConstructor
class ConsumptionGoalController {

	private final CommandConsumptionGoalUseCase commandConsumptionGoalUseCase;
	private final QueryConsumptionGoalUseCase queryConsumptionGoalUseCase;

	@GetMapping("/{userId}/{yearMonth}")
	ResponseEntity<UserMonthlyConsumptionGoalListResponse> findMonthlyUserConsumptionGoal(
		@PathVariable Long userId,
		@PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth
	) {
		LocalDate thisMonth = yearMonth.atDay(1);
		List<QueryConsumptionGoalResult> results = queryConsumptionGoalUseCase.findMonthlyUserConsumptionGoal(thisMonth,
			userId);
		UserMonthlyConsumptionGoalListResponse response = new UserMonthlyConsumptionGoalListResponse(results);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/{userId}")
	ResponseEntity<HttpStatus> updateThisMonthCaps(
		@PathVariable Long userId,
		@RequestBody List<UpdateCapRequest> request
	) {
		UpdateCapsCommand command = new UpdateCapsCommand(userId, request);
		commandConsumptionGoalUseCase.updateMultipleThisMonthCaps(command);

		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
