package com.prography.budgetbuddiesbackend.common.response.error;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class ApiErrorResponse {

	private final String code;              // 예: "INVALID_INPUT"
	private final String message;           // 예: "입력값이 올바르지 않습니다."
	private final int status;               // 예: 400
	private final String path;              // 요청 URI
	private final LocalDateTime timestamp;  // 에러 발생 시각

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Object errors;

	public static ApiErrorResponse of(String code, String message, int status, String path) {
		return of(code, message, status, path, null);
	}

	public static ApiErrorResponse of(String code, String message, int status, String path, Object errors) {
		return new ApiErrorResponse(code, message, status, path, LocalDateTime.now(), errors);
	}
}