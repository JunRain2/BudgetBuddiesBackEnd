package com.prography.budgetbuddiesbackend.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI() // OpenAPI의 사양을 정의하는 객체
			.components(new Components()) // API에 사용할 공통 컴포넌트를 정의
			.info(new Info() // API 문서의 기본 정보를 설정
				.title("API Documentation")
				.description("Spring Boot REST API Documentation")
				.version("1.0.0"));
	}
}
