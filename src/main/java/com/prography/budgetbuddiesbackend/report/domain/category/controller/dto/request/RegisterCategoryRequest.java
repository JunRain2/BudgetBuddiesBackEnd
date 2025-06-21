package com.prography.budgetbuddiesbackend.report.domain.category.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterCategoryRequest(
	@NotBlank @Size(min = 1, max = 20, message = "이름의 크기는 1자 이상 20자 이하여야 합니다.") String name) {
}
