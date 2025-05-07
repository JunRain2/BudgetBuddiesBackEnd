package com.prography.budgetbuddiesbackend.common.response;

import static com.prography.budgetbuddiesbackend.common.response.ResultCode.*;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
	private final String code;
	private final String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	public static ApiResponse<Void> success() {
		return new ApiResponse<>(SUCCESS.getCode(), SUCCESS.getMessage(), null);
	}

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(SUCCESS.getCode(), SUCCESS.getMessage(), data);
	}

	public static <T> ApiResponse<T> of(ResultCode code) {
		return new ApiResponse<>(code.getCode(), code.getMessage(), null);
	}

	public static <T> ApiResponse<T> of(ResultCode code, T data) {
		return new ApiResponse<>(code.getCode(), code.getMessage(), data);
	}
}