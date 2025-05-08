package com.prography.budgetbuddiesbackend.common.response.error;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.prography.budgetbuddiesbackend.common.response.ResultCode;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {
		ResultCode code = ex.getCode();

		ApiErrorResponse response = ApiErrorResponse.of(
			code.getCode(),
			code.getMessage(),
			code.getHttpStatus().value(),
			request.getRequestURI(),
			LocalDateTime.now(),
			ex.getErrors());

		return ResponseEntity.status(code.getHttpStatus()).body(response);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException ex,
		HttpServletRequest request) {

		List<ErrorDetail> errors = ex.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(error -> new ErrorDetail(error.getField(), error.getDefaultMessage()))
			.toList();

		ResultCode code = ResultCode.INVALID_INPUT;

		ApiErrorResponse response = ApiErrorResponse.of(
			code.getCode(),
			code.getMessage(),
			code.getHttpStatus().value(),
			request.getRequestURI(),
			LocalDateTime.now(),
			errors);

		return ResponseEntity.status(code.getHttpStatus()).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleUnexpectedException(Exception ex, HttpServletRequest request) {
		ResultCode code = ResultCode.INTERNAL_ERROR;

		ApiErrorResponse response = ApiErrorResponse.of(
			code.getCode(),
			code.getMessage(),
			code.getHttpStatus().value(),
			request.getRequestURI(),
			LocalDateTime.now());

		return ResponseEntity.status(code.getHttpStatus()).body(response);
	}
}


