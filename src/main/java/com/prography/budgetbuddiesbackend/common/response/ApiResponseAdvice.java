package com.prography.budgetbuddiesbackend.common.response;

import java.util.Objects;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.prography.budgetbuddiesbackend.common.response.error.BusinessException;

@RestControllerAdvice
class ApiResponseAdvice implements ResponseBodyAdvice<Object> {
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return ApiResponse.class.isAssignableFrom(returnType.getParameterType());
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
		Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
		ServerHttpResponse response) {

		if (Objects.isNull(body)) {
			throw new BusinessException(ResultCode.INTERNAL_ERROR);
		}

		ApiResponse<?> api = (ApiResponse<?>)body;
		return ResponseEntity.status(api.getResultCode().getHttpStatus()).body(api);
	}
}
