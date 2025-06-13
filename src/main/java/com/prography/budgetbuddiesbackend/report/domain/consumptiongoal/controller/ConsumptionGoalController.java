package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.controller;

import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.dto.UserConsumptionGoalResponse;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service.ConsumptionGoalFacadeService;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;
import com.prography.budgetbuddiesbackend.report.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/consumption-goals")
@RequiredArgsConstructor
public class ConsumptionGoalController {
    private final ConsumptionGoalFacadeService consumptionGoalService;
    private final UserService userService;

    @GetMapping
    public List<UserConsumptionGoalResponse> getUserConsumptionGoalsByMonth(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth
    ) {
        User user = userService.findById(userId);
        return consumptionGoalService.getUserConsumptionGoalsByMonth(user, yearMonth);
    }
} 