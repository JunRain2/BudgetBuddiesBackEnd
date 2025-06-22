package com.prography.budgetbuddiesbackend.common.response;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultCode {
	SUCCESS("SUCCESS", "요청이 성공했습니다.", HttpStatus.OK),

	INVALID_INPUT("INVALID_INPUT", "입력값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
	NOT_FOUND("NOT_FOUND", "요청한 리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
	DUPLICATE("DUPLICATE", "중복된 요청입니다.", HttpStatus.CONFLICT),
	UNAUTHORIZED("UNAUTHORIZED", "인증이 필요합니다.", HttpStatus.UNAUTHORIZED),
	FORBIDDEN("FORBIDDEN", "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
	INTERNAL_ERROR("INTERNAL_ERROR", "서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

	private static final Map<HttpStatus, ResultCode> BY_STATUS =
		Arrays.stream(ResultCode.values())
			.collect(Collectors.toMap(c -> c.getHttpStatus(), c -> c));
	private final String code;
	private final String message;
	private final HttpStatus httpStatus;

	public static ResultCode getOrDefault(HttpStatus code) {
		return BY_STATUS.getOrDefault(code, INTERNAL_ERROR);
	}
}
