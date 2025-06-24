package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record UserConsumptionGoalResponse(UUID consumptionGoalId, String categoryName, Integer cap,
                                          Integer totalSpent) {

    @JsonProperty("remainingAmount")
    public Integer remainingAmount() {
        return cap - totalSpent;
    }
}
