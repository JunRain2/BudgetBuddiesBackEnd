package com.prography.budgetbuddiesbackend.report.domain.category.controller;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.prography.budgetbuddiesbackend.common.AbstractControllerTest;
import com.prography.budgetbuddiesbackend.report.domain.category.controller.dto.request.RegisterCategoryRequest;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.repository.CategoryRepository;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;
import com.prography.budgetbuddiesbackend.report.domain.expense.repository.ExpenseRepository;
import com.prography.budgetbuddiesbackend.user.entity.User;
import com.prography.budgetbuddiesbackend.user.repository.UserRepository;

import io.restassured.http.ContentType;

class CategoryControllerIntegrationTest extends AbstractControllerTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ExpenseRepository expenseRepository;

	private Long userId;

	@BeforeEach
	void setUp() {
		// 테스트용 User 생성
		User user = User.of();
		User savedUser = userRepository.save(user);
		// userId = savedUser.getId();
	}

	@Test
	@DisplayName("카테고리 등록 API - 정상 응답")
	void registerCategory_success() {
		// given
		RegisterCategoryRequest request = new RegisterCategoryRequest("식비");
		// when & then
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", userId)
			.body(request)
			.when()
			.post("/api/categories")
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("code", is("SUCCESS"))
			.body("message", is("요청이 성공했습니다."));
	}

	@Test
	@DisplayName("사용자 카테고리 조회 API - 정상 응답")
	void getUserCategories_success() {
		// given
		RegisterCategoryRequest request = new RegisterCategoryRequest("식비");
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", userId)
			.body(request)
			.when()
			.post("/api/categories");

		// when & then
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", userId)
			.when()
			.get("/api/categories")
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("code", is("SUCCESS"))
			.body("message", is("요청이 성공했습니다."))
			.body("data", notNullValue())
			.body("data.size()", greaterThan(0));
	}

	@Test
	@DisplayName("카테고리 등록 API - 중복 이름으로 등록 시 실패")
	void registerCategory_duplicateName_failure() {
		// given
		RegisterCategoryRequest request = new RegisterCategoryRequest("식비");
		// 먼저 카테고리 생성
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", userId)
			.body(request)
			.when()
			.post("/api/categories");

		// when & then - 중복 이름으로 다시 생성 시도
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", userId)
			.body(request)
			.when()
			.post("/api/categories")
			.then()
			.statusCode(HttpStatus.CONFLICT.value())
			.body("code", is("DUPLICATE"))
			.body("message", is("중복된 요청입니다."))
			.body("status", is(409))
			.body("path", is("/api/categories"))
			.body("timestamp", notNullValue())
			.body("errors", notNullValue())
			.body("errors[0].field", is("categoryName"))
			.body("errors[0].reason", is("중복된 카테고리 명이 존재합니다."));
	}

	@Test
	@DisplayName("카테고리 등록 API - 빈 이름으로 등록 시 실패")
	void registerCategory_emptyName_failure() {
		// given
		RegisterCategoryRequest request = new RegisterCategoryRequest("");
		// when & then
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", userId)
			.body(request)
			.when()
			.post("/api/categories")
			.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("code", is("INVALID_INPUT"))
			.body("message", is("입력값이 올바르지 않습니다."))
			.body("status", is(400))
			.body("path", is("/api/categories"))
			.body("timestamp", notNullValue())
			.body("errors", notNullValue())
			.body("errors[0].field", is("name"))
			.body("errors[1].field", is("name"));
	}

	@Test
	@DisplayName("카테고리 등록 API - 21자 초과 이름으로 등록 시 실패")
	void registerCategory_tooLongName_failure() {
		// given - 20자 제한이므로 21자로 테스트
		RegisterCategoryRequest request = new RegisterCategoryRequest("가".repeat(21));
		// when & then
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", userId)
			.body(request)
			.when()
			.post("/api/categories")
			.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("code", is("INVALID_INPUT"))
			.body("message", is("입력값이 올바르지 않습니다."))
			.body("status", is(400))
			.body("path", is("/api/categories"))
			.body("timestamp", notNullValue())
			.body("errors", notNullValue())
			.body("errors[0].field", is("name"))
			.body("errors[0].reason", is("이름의 크기는 1자 이상 20자 이하여야 합니다."));
	}

	@Test
	@DisplayName("사용자 카테고리 조회 API - 카테고리가 없을 때 빈 리스트 반환")
	void getUserCategories_emptyList() {
		// when & then
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", userId)
			.when()
			.get("/api/categories")
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("code", is("SUCCESS"))
			.body("message", is("요청이 성공했습니다."))
			.body("data", notNullValue());
	}

	@Test
	@DisplayName("카테고리 삭제 API - 정상 응답")
	void deleteCategory_success() {
		// given
		RegisterCategoryRequest request = new RegisterCategoryRequest("삭제할카테고리");
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", userId)
			.body(request)
			.when()
			.post("/api/categories");

		// 생성된 카테고리 ID 조회
		Category category = categoryRepository.findUserCategoriesByUserIdOrType(userId, null)
			.stream()
			.filter(c -> "삭제할카테고리".equals(c.getName()))
			.findFirst()
			.orElseThrow();

		// when & then
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", userId)
			.when()
			.delete("/api/categories/" + category.getId())
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("code", is("SUCCESS"))
			.body("message", is("요청이 성공했습니다."));
	}

	@Test
	@DisplayName("카테고리 삭제 API - 지출이 있는 카테고리 삭제 시 정상 처리")
	void deleteCategory_withExpenses_success() {
		// given
		RegisterCategoryRequest request = new RegisterCategoryRequest("지출있는카테고리");
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", userId)
			.body(request)
			.when()
			.post("/api/categories");

		// 생성된 카테고리 ID 조회
		Category category = categoryRepository.findUserCategoriesByUserIdOrType(userId, null)
			.stream()
			.filter(c -> "지출있는카테고리".equals(c.getName()))
			.findFirst()
			.orElseThrow();

		// 지출 데이터 생성
		Expense expense = Expense.of(userId, category, 10000, "테스트 지출", LocalDate.now());
		expenseRepository.save(expense);

		// when & then
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", userId)
			.when()
			.delete("/api/categories/" + category.getId())
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("code", is("SUCCESS"))
			.body("message", is("요청이 성공했습니다."));
	}

	@Test
	@DisplayName("카테고리 삭제 API - 존재하지 않는 카테고리 삭제 시 실패")
	void deleteCategory_notFound_failure() {
		// when & then
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", userId)
			.when()
			.delete("/api/categories/99999")
			.then()
			.statusCode(HttpStatus.NOT_FOUND.value())
			.body("code", is("NOT_FOUND"))
			.body("message", is("요청한 리소스를 찾을 수 없습니다."))
			.body("status", is(404))
			.body("path", is("/api/categories/99999"))
			.body("timestamp", notNullValue())
			.body("errors", notNullValue())
			.body("errors[0].field", is("categoryId"))
			.body("errors[0].reason", is("존재하지 않는 카테고리입니다."));
	}

	@Test
	@DisplayName("카테고리 삭제 API - 기본 카테고리 삭제 시 실패")
	void deleteCategory_defaultCategory_failure() {
		// given - 기본 카테고리 ID (마이그레이션에서 생성된 기본 카테고리)
		Long defaultCategoryId = 1L;

		// when & then
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", userId)
			.when()
			.delete("/api/categories/" + defaultCategoryId)
			.then()
			.statusCode(HttpStatus.FORBIDDEN.value())
			.body("code", is("FORBIDDEN"))
			.body("message", is("접근 권한이 없습니다."))
			.body("status", is(403))
			.body("path", is("/api/categories/" + defaultCategoryId))
			.body("timestamp", notNullValue())
			.body("errors", notNullValue())
			.body("errors[0].field", is("category"))
			.body("errors[0].reason", is("사용자가 수정이 불가능한 카테고리입니다."));
	}

	@Test
	@DisplayName("카테고리 등록 API - 경계값 테스트 (1자 이름)")
	void registerCategory_oneCharacterName_success() {
		// given
		RegisterCategoryRequest request = new RegisterCategoryRequest("가");
		// when & then
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", userId)
			.body(request)
			.when()
			.post("/api/categories")
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("code", is("SUCCESS"))
			.body("message", is("요청이 성공했습니다."));
	}

	@Test
	@DisplayName("카테고리 등록 API - 경계값 테스트 (20자 이름)")
	void registerCategory_twentyCharacterName_success() {
		// given
		RegisterCategoryRequest request = new RegisterCategoryRequest("가".repeat(20));
		// when & then
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", userId)
			.body(request)
			.when()
			.post("/api/categories")
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("code", is("SUCCESS"))
			.body("message", is("요청이 성공했습니다."));
	}

	@Test
	@DisplayName("카테고리 등록 API - 특수문자 포함 이름으로 등록 시 정상 응답")
	void registerCategory_specialCharacters_success() {
		// given
		RegisterCategoryRequest request = new RegisterCategoryRequest("식비-카테고리");
		// when & then
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", userId)
			.body(request)
			.when()
			.post("/api/categories")
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("code", is("SUCCESS"))
			.body("message", is("요청이 성공했습니다."));
	}

	@Test
	@DisplayName("카테고리 등록 API - 영어 이름으로 등록 시 정상 응답")
	void registerCategory_englishName_success() {
		// given
		RegisterCategoryRequest request = new RegisterCategoryRequest("Food");
		// when & then
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", userId)
			.body(request)
			.when()
			.post("/api/categories")
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("code", is("SUCCESS"))
			.body("message", is("요청이 성공했습니다."));
	}

	@Test
	@DisplayName("카테고리 등록 API - 숫자 포함 이름으로 등록 시 정상 응답")
	void registerCategory_numberInName_success() {
		// given
		RegisterCategoryRequest request = new RegisterCategoryRequest("식비1");
		// when & then
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", userId)
			.body(request)
			.when()
			.post("/api/categories")
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("code", is("SUCCESS"))
			.body("message", is("요청이 성공했습니다."));
	}

	@Test
	@DisplayName("사용자 카테고리 조회 API - 다른 사용자의 카테고리는 조회되지 않음")
	void getUserCategories_otherUserIsolation() {
		// given - 다른 사용자 생성
		User otherUser = User.of();
		User savedOtherUser = userRepository.save(otherUser);

		// 다른 사용자의 카테고리 생성
		RegisterCategoryRequest request = new RegisterCategoryRequest("다른사용자카테고리");
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", savedOtherUser.getId())
			.body(request)
			.when()
			.post("/api/categories");

		// when & then - 원래 사용자로 조회
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", userId)
			.when()
			.get("/api/categories")
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("code", is("SUCCESS"))
			.body("message", is("요청이 성공했습니다."))
			.body("data", notNullValue())
			.body("data.findAll { it.name == '다른사용자카테고리' }.size()", is(0));
	}

	@Test
	@DisplayName("카테고리 삭제 API - 다른 사용자의 카테고리 삭제 시 실패")
	void deleteCategory_otherUserCategory_failure() {
		// given - 다른 사용자 생성
		User otherUser = User.of();
		User savedOtherUser = userRepository.save(otherUser);

		// 다른 사용자의 카테고리 생성
		RegisterCategoryRequest request = new RegisterCategoryRequest("다른사용자카테고리");
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", savedOtherUser.getId())
			.body(request)
			.when()
			.post("/api/categories");

		// 생성된 카테고리 ID 조회
		Category category = categoryRepository.findUserCategoriesByUserIdOrType(savedOtherUser.getId(), null)
			.stream()
			.filter(c -> "다른사용자카테고리".equals(c.getName()))
			.findFirst()
			.orElseThrow();

		// when & then - 원래 사용자로 삭제 시도
		given()
			.contentType(ContentType.JSON)
			.queryParam("userId", userId)
			.when()
			.delete("/api/categories/" + category.getId())
			.then()
			.statusCode(HttpStatus.FORBIDDEN.value())
			.body("code", is("FORBIDDEN"))
			.body("message", is("접근 권한이 없습니다."))
			.body("status", is(403))
			.body("path", is("/api/categories/" + category.getId()))
			.body("timestamp", notNullValue())
			.body("errors", notNullValue())
			.body("errors[0].field", is("category"))
			.body("errors[0].reason", is("사용자가 수정이 불가능한 카테고리입니다."));
	}

	@Test
	@DisplayName("카테고리 삭제 API - 기본 카테고리 ID 2~10 삭제 시 실패")
	void deleteCategory_defaultCategories2to10_failure() {
		// given - 기본 카테고리 ID들 (마이그레이션에서 생성된 기본 카테고리들)
		for (long categoryId = 2; categoryId <= 10; categoryId++) {
			given()
				.contentType(ContentType.JSON)
				.queryParam("userId", userId)
				.when()
				.delete("/api/categories/" + categoryId)
				.then()
				.statusCode(HttpStatus.FORBIDDEN.value())
				.body("code", is("FORBIDDEN"))
				.body("message", is("접근 권한이 없습니다."))
				.body("status", is(403))
				.body("path", is("/api/categories/" + categoryId))
				.body("timestamp", notNullValue())
				.body("errors", notNullValue())
				.body("errors[0].field", is("category"))
				.body("errors[0].reason", is("사용자가 수정이 불가능한 카테고리입니다."));
		}
	}
}