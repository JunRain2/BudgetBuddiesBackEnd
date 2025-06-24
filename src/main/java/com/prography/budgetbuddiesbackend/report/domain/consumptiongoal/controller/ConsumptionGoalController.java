package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.controller;

import com.prography.budgetbuddiesbackend.common.annotation.CurrentUserId;
import com.prography.budgetbuddiesbackend.common.response.ApiResponse;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.controller.dto.BatchUpdateConsumptionGoalCapRequest;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.controller.dto.UserConsumptionGoalResponse;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service.ConsumptionGoalUseCase;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service.command.BatchUpdateCapCommand;
import com.prography.budgetbuddiesbackend.user.entity.UserId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consumption-goals")
@Validated
@RequiredArgsConstructor
public class ConsumptionGoalController {

    private final ConsumptionGoalUseCase consumptionGoalService;
    private final ConsumptionGoalCommandMapper mapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserConsumptionGoalResponse>>> getUserConsumptionGoalsByMonth(
        @NotNull @RequestParam @PastOrPresent @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth,
        @CurrentUserId @RequestParam UUID userId) {

        UserId userKey = UserId.of(userId);

        List<UserConsumptionGoalResponse> result = consumptionGoalService.getUserConsumptionGoalsByMonth(
            userKey, yearMonth);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PatchMapping("/batch-cap")
    public ResponseEntity<ApiResponse<Void>> batchUpdateConsumptionGoalCap(
        @RequestBody @Valid BatchUpdateConsumptionGoalCapRequest request,
        @CurrentUserId @RequestParam UUID userId) {

        BatchUpdateCapCommand command = mapper.requestToBatchUpdateCapCommand(request);
        UserId userKey = UserId.of(userId);

        consumptionGoalService.batchUpdateCapForThisMonth(userKey, command);

        return ResponseEntity.ok(ApiResponse.success());
    }
} 