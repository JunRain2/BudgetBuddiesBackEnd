package com.prography.budgetbuddiesbackend.report.domain.category.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.prography.budgetbuddiesbackend.common.AbstractControllerTest;
import com.prography.budgetbuddiesbackend.report.domain.category.controller.dto.request.RegisterCategoryRequest;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryType;
import com.prography.budgetbuddiesbackend.report.domain.category.repository.CategoryRepository;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;
import com.prography.budgetbuddiesbackend.report.domain.expense.repository.ExpenseRepository;
import com.prography.budgetbuddiesbackend.user.entity.User;
import com.prography.budgetbuddiesbackend.user.repository.UserRepository;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

class CategoryControllerIntegrationTest extends AbstractControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    private UUID userId;
    private Category category;
    private User savedUser;

    @BeforeEach
    void setUp() {
        User user = User.of();
        savedUser = userRepository.save(user);
        userId = savedUser.getId().getId();

        category = Category.of(savedUser.getId(), "테스트카테고리");
        categoryRepository.save(category);
    }

    @Test
    @DisplayName("카테고리 등록 API - 정상 응답")
    void registerCategory_success() {
        // given
        RegisterCategoryRequest request = new RegisterCategoryRequest("새로운 카테고리");
        // when & then
        given().contentType(ContentType.JSON).queryParam("userId", userId).body(request).when()
            .post("/api/categories").then().statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS")).body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("사용자 카테고리 조회 API - 정상 응답")
    void getUserCategories_success() {
        // given
        RegisterCategoryRequest request = new RegisterCategoryRequest("식비");
        given().contentType(ContentType.JSON).queryParam("userId", userId).body(request).when()
            .post("/api/categories");

        // when & then
        given().contentType(ContentType.JSON).queryParam("userId", userId).when()
            .get("/api/categories").then().statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS")).body("message", is("요청이 성공했습니다."))
            .body("data", notNullValue()).body("data.size()", greaterThan(0));
    }

    @Test
    @DisplayName("카테고리 등록 API - 중복 이름으로 등록 시 실패")
    void registerCategory_duplicateName_failure() {
        // given
        RegisterCategoryRequest request = new RegisterCategoryRequest("테스트카테고리");
        // 먼저 카테고리 생성
        given().contentType(ContentType.JSON).queryParam("userId", userId).body(request).when()
            .post("/api/categories");

        // when & then - 중복 이름으로 다시 생성 시도
        given().contentType(ContentType.JSON).queryParam("userId", userId).body(request).when()
            .post("/api/categories").then().statusCode(HttpStatus.CONFLICT.value())
            .body("code", is("DUPLICATE")).body("message", is("중복된 요청입니다.")).body("status", is(409))
            .body("path", is("/api/categories")).body("timestamp", notNullValue())
            .body("errors", notNullValue()).body("errors[0].field", is("categoryName"))
            .body("errors[0].reason", is("중복된 카테고리 명이 존재합니다."));
    }

    @Test
    @DisplayName("카테고리 등록 API - 빈 이름으로 등록 시 실패")
    void registerCategory_emptyName_failure() {
        // given
        RegisterCategoryRequest request = new RegisterCategoryRequest("");
        // when & then
        given().contentType(ContentType.JSON).queryParam("userId", userId).body(request).when()
            .post("/api/categories").then().statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", is("INVALID_INPUT")).body("message", is("입력값이 올바르지 않습니다."))
            .body("status", is(400)).body("path", is("/api/categories"))
            .body("timestamp", notNullValue()).body("errors", notNullValue())
            .body("errors[0].field", is("name")).body("errors[1].field", is("name"));
    }

    @Test
    @DisplayName("카테고리 등록 API - 21자 초과 이름으로 등록 시 실패")
    void registerCategory_tooLongName_failure() {
        // given - 20자 제한이므로 21자로 테스트
        RegisterCategoryRequest request = new RegisterCategoryRequest("가".repeat(21));
        // when & then
        given().contentType(ContentType.JSON).queryParam("userId", userId).body(request).when()
            .post("/api/categories").then().statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", is("INVALID_INPUT")).body("message", is("입력값이 올바르지 않습니다."))
            .body("status", is(400)).body("path", is("/api/categories"))
            .body("timestamp", notNullValue()).body("errors", notNullValue())
            .body("errors[0].field", is("name"))
            .body("errors[0].reason", is("이름의 크기는 1자 이상 20자 이하여야 합니다."));
    }

    @Test
    @DisplayName("사용자 카테고리 조회 API - 카테고리가 없을 때 빈 리스트 반환")
    void getUserCategories_emptyList() {
        // when & then
        given().contentType(ContentType.JSON).queryParam("userId", userId).when()
            .get("/api/categories").then().statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS")).body("message", is("요청이 성공했습니다."))
            .body("data", notNullValue());
    }

    @Test
    @DisplayName("카테고리 삭제 API - 정상 응답")
    void deleteCategory_success() {
        // when & then
        given().contentType(ContentType.JSON).queryParam("userId", userId).when()
            .delete("/api/categories/{categoryId}", category.getId().getId()).then()
            .statusCode(HttpStatus.OK.value()).body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("카테고리 삭제 API - 지출이 있는 카테고리 삭제 시 정상 처리")
    void deleteCategory_withExpenses_success() {
        // given
        Expense expense = Expense.of(savedUser.getId(), category, 10000, "점심값", LocalDate.now());
        expenseRepository.save(expense);

        // when & then
        given().contentType(ContentType.JSON).queryParam("userId", userId).when()
            .delete("/api/categories/" + category.getId().getId()).then()
            .statusCode(HttpStatus.OK.value()).body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("카테고리 삭제 API - 존재하지 않는 카테고리 삭제 시 실패")
    void deleteCategory_notFound_failure() {
        UUID randomUUID = UUID.randomUUID();

        // when & then
        given().contentType(ContentType.JSON).queryParam("userId", userId).when()
            .delete("/api/categories/{categoryId}", randomUUID).then()
            .statusCode(HttpStatus.NOT_FOUND.value()).body("code", is("NOT_FOUND"))
            .body("message", is("요청한 리소스를 찾을 수 없습니다.")).body("status", is(404))
            .body("path", is("/api/categories/" + randomUUID)).body("timestamp", notNullValue())
            .body("errors", notNullValue()).body("errors[0].field", is("consumptionGoalId"))
            .body("errors[0].reason", is("존재하지 않는 카테고리입니다."));
    }

    @Test
    @DisplayName("카테고리 삭제 API - 수정 불가능한 카테고리 삭제 시 실패")
    void deleteCategory_unmodifiable_failure() {
        // given
        Category unmodifiableCategory = Category.of(savedUser.getId(), "수정불가카테고리");
        categoryRepository.save(unmodifiableCategory);

        // when & then
        given().contentType(ContentType.JSON).queryParam("userId", UUID.randomUUID()).when()
            .delete("/api/categories/{categoryId}", unmodifiableCategory.getId().getId()).then()
            .statusCode(HttpStatus.FORBIDDEN.value()).body("code", is("FORBIDDEN"))
            .body("message", is("접근 권한이 없습니다.")).body("status", is(403))
            .body("path", is("/api/categories/" + unmodifiableCategory.getId().getId()))
            .body("timestamp", notNullValue()).body("errors", notNullValue())
            .body("errors[0].field", is("category"))
            .body("errors[0].reason", is("사용자가 수정이 불가능한 카테고리입니다."));
    }

    @Test
    @DisplayName("카테고리 등록 API - 경계값 테스트 (1자 이름)")
    void registerCategory_oneCharacterName_success() {
        // given
        RegisterCategoryRequest request = new RegisterCategoryRequest("가");
        // when & then
        given().contentType(ContentType.JSON).queryParam("userId", userId).body(request).when()
            .post("/api/categories").then().statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS")).body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("카테고리 등록 API - 경계값 테스트 (20자 이름)")
    void registerCategory_twentyCharacterName_success() {
        // given
        RegisterCategoryRequest request = new RegisterCategoryRequest("가".repeat(20));
        // when & then
        given().contentType(ContentType.JSON).queryParam("userId", userId).body(request).when()
            .post("/api/categories").then().statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS")).body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("카테고리 등록 API - 특수문자 포함 이름으로 등록 시 정상 응답")
    void registerCategory_specialCharacters_success() {
        // given
        RegisterCategoryRequest request = new RegisterCategoryRequest("식비-카테고리");
        // when & then
        given().contentType(ContentType.JSON).queryParam("userId", userId).body(request).when()
            .post("/api/categories").then().statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS")).body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("카테고리 등록 API - 영어 이름으로 등록 시 정상 응답")
    void registerCategory_englishName_success() {
        // given
        RegisterCategoryRequest request = new RegisterCategoryRequest("Food");
        // when & then
        given().contentType(ContentType.JSON).queryParam("userId", userId).body(request).when()
            .post("/api/categories").then().statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS")).body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("카테고리 등록 API - 숫자 포함 이름으로 등록 시 정상 응답")
    void registerCategory_numberInName_success() {
        // given
        RegisterCategoryRequest request = new RegisterCategoryRequest("식비1");
        // when & then
        given().contentType(ContentType.JSON).queryParam("userId", userId).body(request).when()
            .post("/api/categories").then().statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS")).body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("사용자 카테고리 조회 API - 다른 사용자의 카테고리는 조회되지 않음")
    void getUserCategories_otherUserIsolation() {
        // given
        User otherUser = userRepository.save(User.of());
        UUID otherUserId = otherUser.getId().getId();
        Category otherCategory = Category.of(otherUser.getId(), "다른유저카테고리");
        categoryRepository.save(otherCategory);

        // when & then
        given().contentType(ContentType.JSON).queryParam("userId", userId).when()
            .get("/api/categories").then().statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS")).body("message", is("요청이 성공했습니다."))
            .body("data", notNullValue())
            .body("data.findAll { it.name == '다른유저카테고리' }.size()", is(0));
    }

    @Test
    @DisplayName("카테고리 삭제 API - 다른 사용자의 카테고리 삭제 시 실패")
    void deleteCategory_otherUserCategory_failure() {
        // given
        User otherUser = userRepository.save(User.of());
        Category otherCategory = Category.of(otherUser.getId(), "남의카테고리");
        categoryRepository.save(otherCategory);

        // when & then
        given().contentType(ContentType.JSON).queryParam("userId", userId).when()
            .delete("/api/categories/{categoryId}", otherCategory.getId().getId()).then()
            .statusCode(HttpStatus.FORBIDDEN.value()).body("code", is("FORBIDDEN"))
            .body("message", is("접근 권한이 없습니다.")).body("status", is(403))
            .body("path", is("/api/categories/" + otherCategory.getId().getId()))
            .body("timestamp", notNullValue()).body("errors", notNullValue())
            .body("errors[0].field", is("category"))
            .body("errors[0].reason", is("사용자가 수정이 불가능한 카테고리입니다."));
    }

    @Test
    @DisplayName("카테고리 삭제 API - 기본 카테고리 ID 2~10 삭제 시 실패")
    void deleteCategory_defaultCategories_failure() {
        List<UUID> categoryList = categoryRepository.findUserCategoriesByUserIdOrType(null,
            CategoryType.DEFAULT).stream().map(c -> c.getId().getId()).toList();

        // when & then
        for (UUID categoryId : categoryList) {
            given().contentType(ContentType.JSON).queryParam("userId", userId).when()
                .delete("/api/categories/{categoryId}", categoryId).then()
                .statusCode(HttpStatus.FORBIDDEN.value()).body("code", is("FORBIDDEN"))
                .body("message", is("접근 권한이 없습니다.")).body("status", is(403))
                .body("path", is("/api/categories/" + categoryId)).body("timestamp", notNullValue())
                .body("errors", notNullValue()).body("errors[0].field", is("category"))
                .body("errors[0].reason", is("사용자가 수정이 불가능한 카테고리입니다."));
        }
    }
}