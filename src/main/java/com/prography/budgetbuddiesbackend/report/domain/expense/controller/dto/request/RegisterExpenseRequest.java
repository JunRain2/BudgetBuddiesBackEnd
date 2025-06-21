package com.prography.budgetbuddiesbackend.report.domain.expense.controller.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record RegisterExpenseRequest(
	@NotNull Long categoryId,
	@NotNull @Min(value = 1, message = "최소금액은 1원 이상이여야 합니다.") Integer amount,
	@NotBlank @Size(min = 1, max = 30, message = "설명은 1글자 이상 30글자 이하로 입력해야 합니다.") String description,
	@NotNull @PastOrPresent LocalDate expenseAt
) {
}
