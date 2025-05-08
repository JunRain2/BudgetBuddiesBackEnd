package com.prography.budgetbuddiesbackend.common.response;

import static com.prography.budgetbuddiesbackend.common.response.ResultCode.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
	@JsonIgnore
	private final ResultCode code;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	public static ApiResponse<Void> success() {
		return new ApiResponse<>(SUCCESS, null);
	}

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(SUCCESS, data);
	}

	public static <T> ApiResponse<T> of(ResultCode code) {
		return new ApiResponse<>(code, null);
	}

	public static <T> ApiResponse<T> of(ResultCode code, T data) {
		return new ApiResponse<>(code, data);
	}

	@JsonProperty("code")
	public String getStatus() {
		return code.getCode();  // ResultCode 내부 status 필드
	}

	@JsonProperty("message")
	public String getMessage() {
		return code.getMessage(); // ResultCode 내부 message 필드
	}

}