package com.prography.budgetbuddiesbackend.report.domain.expense.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.UUID;

public record UpdateExpenseRequest(
    @NotNull UUID expenseId,
    @NotNull UUID categoryId,
    @NotNull @PastOrPresent LocalDate expenseAt
) {

}
