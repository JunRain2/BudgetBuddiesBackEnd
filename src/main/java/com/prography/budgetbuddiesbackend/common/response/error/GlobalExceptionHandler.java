package com.prography.budgetbuddiesbackend.common.response.error;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.prography.budgetbuddiesbackend.common.response.ResultCode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {
		ResultCode code = ex.getCode();

		ApiErrorResponse response = ApiErrorResponse.of(
			code.getCode(),
			code.getMessage(),
			code.getHttpStatus().value(),
			request.getRequestURI(),
			ex.getErrors());

		return ResponseEntity.status(code.getHttpStatus()).body(response);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiErrorResponse> handleConstraintViolationException(ConstraintViolationException ex,
		HttpServletRequest request) {

		ResultCode code = ResultCode.INVALID_INPUT;

		ApiErrorResponse response = ApiErrorResponse.of(
			code.getCode(),
			code.getMessage(),
			code.getHttpStatus().value(),
			request.getRequestURI(),
			ex.getMessage());

		return ResponseEntity.status(code.getHttpStatus()).body(response);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<ErrorDetail> errors = ex.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(error -> new ErrorDetail(error.getField(), error.getDefaultMessage()))
			.toList();

		return super.handleExceptionInternal(ex, errors, headers, status, request);
	}

	/**
	 * 해당 메서드를 감싸서 반환하는 것을 권장
	 *
	 * @param body the body to use for the response
	 * @param headers the headers to use for the response
	 * @param statusCode the status code to use for the response
	 * @param request the current request
	 * @return API 예외에 대한 공통 스펙인 ApiErrorResponse를 감싼 ResponseEntity 반환
	 */
	@Override
	protected ResponseEntity<Object> createResponseEntity(Object body, HttpHeaders headers, HttpStatusCode statusCode,
		WebRequest request) {

		ResultCode code = ResultCode.getOrDefault(HttpStatus.valueOf(statusCode.value()));
		String path = ((ServletWebRequest)request).getRequest().getRequestURI();

		ApiErrorResponse response = ApiErrorResponse.of(
			code.getCode(),
			code.getMessage(),
			code.getHttpStatus().value(),
			path,
			body);

		return new ResponseEntity<>(response, headers, statusCode);
	}
}


