package com.prography.budgetbuddiesbackend.report.domain.expense.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.prography.budgetbuddiesbackend.common.AbstractControllerTest;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryId;
import com.prography.budgetbuddiesbackend.report.domain.category.repository.CategoryRepository;
import com.prography.budgetbuddiesbackend.report.domain.expense.controller.dto.request.RegisterExpenseRequest;
import com.prography.budgetbuddiesbackend.report.domain.expense.controller.dto.request.UpdateExpenseRequest;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.ExpenseId;
import com.prography.budgetbuddiesbackend.report.domain.expense.repository.ExpenseRepository;
import com.prography.budgetbuddiesbackend.user.entity.User;
import com.prography.budgetbuddiesbackend.user.entity.UserId;
import com.prography.budgetbuddiesbackend.user.repository.UserRepository;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

class ExpenseControllerIntegrationTest extends AbstractControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    private UserId userId;
    private CategoryId categoryId;
    private ExpenseId expenseId;
    private Category category;
    private Expense expense;
    private User savedUser;

    @BeforeEach
    void setUp() {
        User user = User.of();
        savedUser = userRepository.save(user);
        userId = savedUser.getId();

        category = Category.of(savedUser.getId(), "테스트카테고리");
        Category savedCategory = categoryRepository.save(category);
        categoryId = savedCategory.getId();

        expense = Expense.of(savedUser.getId(), category, 10000, "점심",
            LocalDate.now().minusDays(1));
        expenseRepository.save(expense);
        expenseId = expense.getExpenseId();
    }

    @Test
    @DisplayName("지출 등록 API - 정상 응답")
    void registerExpense_success() {
        // given
        RegisterExpenseRequest request = new RegisterExpenseRequest(
            categoryId.getId(), 10000, "점심", LocalDate.now().minusDays(1));
        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(request)
            .when()
            .post("/api/expenses")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("지출 등록 API - 오늘 날짜로 등록 시 정상 응답")
    void registerExpense_today_success() {
        // given
        RegisterExpenseRequest request = new RegisterExpenseRequest(
            categoryId.getId(), 15000, "저녁", LocalDate.now());
        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(request)
            .when()
            .post("/api/expenses")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("지출 등록 API - 미래 날짜로 등록 시 실패")
    void registerExpense_futureDate_failure() {
        // given
        RegisterExpenseRequest request = new RegisterExpenseRequest(
            categoryId.getId(),
            10000,
            "점심",
            LocalDate.now().plusDays(1) // 미래 날짜
        );

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(request)
            .when()
            .post("/api/expenses")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", is("INVALID_INPUT"))
            .body("message", is("입력값이 올바르지 않습니다."))
            .body("status", is(400))
            .body("path", is("/api/expenses"))
            .body("timestamp", notNullValue());
    }

    @Test
    @DisplayName("지출 등록 API - 음수 금액으로 등록 시 실패")
    void registerExpense_negativeAmount_failure() {
        // given
        RegisterExpenseRequest request = new RegisterExpenseRequest(
            categoryId.getId(),
            -1000, // 음수
            "점심",
            LocalDate.now()
        );

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(request)
            .when()
            .post("/api/expenses")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", is("INVALID_INPUT"))
            .body("message", is("입력값이 올바르지 않습니다."))
            .body("status", is(400))
            .body("path", is("/api/expenses"))
            .body("timestamp", notNullValue())
            .body("errors", notNullValue())
            .body("errors[0].field", is("amount"))
            .body("errors[0].reason", is("최소금액은 1원 이상이여야 합니다."));
    }

    @Test
    @DisplayName("지출 등록 API - 0원으로 등록 시 실패")
    void registerExpense_zeroAmount_failure() {
        // given
        RegisterExpenseRequest request = new RegisterExpenseRequest(
            categoryId.getId(),
            0, // 0원
            "점심",
            LocalDate.now()
        );

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(request)
            .when()
            .post("/api/expenses")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", is("INVALID_INPUT"))
            .body("message", is("입력값이 올바르지 않습니다."))
            .body("status", is(400))
            .body("path", is("/api/expenses"))
            .body("timestamp", notNullValue())
            .body("errors", notNullValue())
            .body("errors[0].field", is("amount"))
            .body("errors[0].reason", is("최소금액은 1원 이상이여야 합니다."));
    }

    @Test
    @DisplayName("지출 등록 API - 존재하지 않는 카테고리로 등록 시 실패")
    void registerExpense_invalidCategory_failure() {
        // given
        RegisterExpenseRequest request = new RegisterExpenseRequest(
            UUID.randomUUID(), 10000, "잘못된 카테고리", LocalDate.now().minusDays(1));
        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(request)
            .when()
            .post("/api/expenses")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .body("code", is("NOT_FOUND"))
            .body("message", is("요청한 리소스를 찾을 수 없습니다."))
            .body("status", is(404))
            .body("path", is("/api/expenses"))
            .body("timestamp", notNullValue())
            .body("errors", notNullValue())
            .body("errors[0].field", is("consumptionGoalId"))
            .body("errors[0].reason", is("존재하지 않는 카테고리입니다."));
    }

    @Test
    @DisplayName("지출 등록 API - 경계값 테스트 (1원)")
    void registerExpense_oneWon_success() {
        // given
        RegisterExpenseRequest request = new RegisterExpenseRequest(
            categoryId.getId(),
            1, // 1원
            "점심",
            LocalDate.now()
        );

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(request)
            .when()
            .post("/api/expenses")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("지출 등록 API - 경계값 테스트 (최대 금액)")
    void registerExpense_maxAmount_success() {
        // given
        RegisterExpenseRequest request = new RegisterExpenseRequest(
            categoryId.getId(),
            Integer.MAX_VALUE, // 최대 금액
            "점심",
            LocalDate.now()
        );

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(request)
            .when()
            .post("/api/expenses")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("지출 수정 API - 정상 응답")
    void updateExpense_success() {
        // given
        UpdateExpenseRequest request = new UpdateExpenseRequest(
            expenseId.getId(), categoryId.getId(), LocalDate.now().minusDays(1));
        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(request)
            .when()
            .patch("/api/expenses")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("지출 수정 API - 존재하지 않는 지출 수정 시 실패")
    void updateExpense_notFound_failure() {
        // given
        UpdateExpenseRequest request = new UpdateExpenseRequest(
            UUID.randomUUID(), categoryId.getId(), LocalDate.now());
        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(request)
            .when()
            .patch("/api/expenses")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("지출 수정 API - 다른 사용자의 지출 수정 시 실패")
    void updateExpense_otherUserExpense_failure() {
        // given
        User otherUser = userRepository.save(User.of());
        Category otherCategory = Category.of(otherUser.getId(), "다른유저카테고리");
        categoryRepository.save(otherCategory);
        Expense otherExpense = Expense.of(otherUser.getId(), otherCategory, 5000, "다른유저지출",
            LocalDate.now());
        expenseRepository.save(otherExpense);
        UpdateExpenseRequest request = new UpdateExpenseRequest(
            otherExpense.getExpenseId().getId(),
            otherCategory.getId().getId(),
            LocalDate.now()
        );
        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId()) // 내 아이디로
            .body(request)
            .when()
            .patch("/api/expenses") // 남의 지출 수정
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("지출 수정 API - 수정하려는 카테고리가 존재하지 않을 때 실패")
    void updateExpense_categoryNotFound_failure() {
        // given
        UpdateExpenseRequest request = new UpdateExpenseRequest(
            expenseId.getId(),
            UUID.randomUUID(),
            LocalDate.now()
        );
        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(request)
            .when()
            .patch("/api/expenses")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .body("code", is("NOT_FOUND"));
    }


    @Test
    @DisplayName("지출 삭제 API - 정상 응답")
    void deleteExpense_success() {
        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .when()
            .delete("/api/expenses/{expenseId}", expenseId.getId())
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("지출 삭제 API - 존재하지 않는 지출 삭제 시 실패")
    void deleteExpense_notFound_failure() {
        UUID expenseId = UUID.randomUUID();

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .when()
            .delete("/api/expenses/{expenseId}", expenseId)
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .body("code", is("NOT_FOUND"))
            .body("message", is("요청한 리소스를 찾을 수 없습니다."))
            .body("status", is(404))
            .body("path", is("/api/expenses/" + expenseId))
            .body("timestamp", notNullValue());
    }

    @Test
    @DisplayName("지출 삭제 API - 다른 사용자의 지출 삭제 시 실패")
    void deleteExpense_otherUserExpense_failure() {
        // given
        User otherUser = userRepository.save(User.of());
        Category otherCategory = Category.of(otherUser.getId(), "다른유저카테고리");
        categoryRepository.save(otherCategory);
        Expense otherExpense = Expense.of(otherUser.getId(), otherCategory, 5000, "다른유저지출",
            LocalDate.now());
        expenseRepository.save(otherExpense);

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId()) // 내 아이디로
            .when()
            .delete("/api/expenses/{expenseId}", otherExpense.getExpenseId().getId()) // 남의 지출 삭제
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("지출 등록 API - 경계값 테스트 (30자 설명)")
    void registerExpense_thirtyCharacterDescription_success() {
        // given
        Category category = categoryRepository.findById(this.category.getId()).orElseThrow();
        String thirtyCharDescription = "가".repeat(30); // 30자
        RegisterExpenseRequest request = new RegisterExpenseRequest(
            category.getId().getId(),
            10000,
            thirtyCharDescription,
            LocalDate.now()
        );

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(request)
            .when()
            .post("/api/expenses")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("지출 등록 API - 경계값 테스트 (31자 설명)")
    void registerExpense_thirtyOneCharacterDescription_failure() {
        // given - 30자 제한이므로 31자로 테스트
        Category category = categoryRepository.findById(this.category.getId()).orElseThrow();
        RegisterExpenseRequest request = new RegisterExpenseRequest(
            category.getId().getId(),
            10000,
            "가".repeat(31), // 31자
            LocalDate.now()
        );

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(request)
            .when()
            .post("/api/expenses")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", is("INVALID_INPUT"))
            .body("message", is("입력값이 올바르지 않습니다."))
            .body("status", is(400))
            .body("path", is("/api/expenses"))
            .body("timestamp", notNullValue())
            .body("errors", notNullValue())
            .body("errors[0].field", is("description"))
            .body("errors[0].reason", is("설명은 1글자 이상 30글자 이하로 입력해야 합니다."));
    }

    @Test
    @DisplayName("지출 등록 API - 빈 설명으로 등록 시 실패")
    void registerExpense_emptyDescription_failure() {
        // given
        Category category = categoryRepository.findById(this.category.getId()).orElseThrow();
        RegisterExpenseRequest request = new RegisterExpenseRequest(
            category.getId().getId(),
            10000,
            "", // 빈 설명
            LocalDate.now()
        );

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(request)
            .when()
            .post("/api/expenses")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", is("INVALID_INPUT"))
            .body("message", is("입력값이 올바르지 않습니다."))
            .body("status", is(400))
            .body("path", is("/api/expenses"))
            .body("timestamp", notNullValue())
            .body("errors", notNullValue())
            .body("errors[0].field", is("description"))
            .body("errors[1].field", is("description"));
    }

    @Test
    @DisplayName("지출 등록 API - 101자 초과 설명으로 등록 시 실패")
    void registerExpense_tooLongDescription_failure() {
        // given - 100자 제한이므로 101자로 테스트
        Category category = categoryRepository.findById(this.category.getId()).orElseThrow();
        RegisterExpenseRequest request = new RegisterExpenseRequest(
            category.getId().getId(),
            10000,
            "a".repeat(101), // 101자
            LocalDate.now()
        );

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(request)
            .when()
            .post("/api/expenses")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", is("INVALID_INPUT"))
            .body("message", is("입력값이 올바르지 않습니다."))
            .body("status", is(400))
            .body("path", is("/api/expenses"))
            .body("timestamp", notNullValue())
            .body("errors", notNullValue())
            .body("errors[0].field", is("description"))
            .body("errors[0].reason", is("설명은 1글자 이상 30글자 이하로 입력해야 합니다."));
    }

    @Test
    @DisplayName("지출 등록 API - 특수문자 포함 설명으로 등록 시 정상 응답")
    void registerExpense_specialCharactersDescription_success() {
        // given
        Category category = categoryRepository.findById(this.category.getId()).orElseThrow();
        RegisterExpenseRequest request = new RegisterExpenseRequest(
            category.getId().getId(),
            10000,
            "!@#$%^&*()",
            LocalDate.now()
        );

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(request)
            .when()
            .post("/api/expenses")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("지출 등록 API - 영어 설명으로 등록 시 정상 응답")
    void registerExpense_englishDescription_success() {
        // given
        Category category = categoryRepository.findById(this.category.getId()).orElseThrow();
        RegisterExpenseRequest request = new RegisterExpenseRequest(
            category.getId().getId(),
            10000,
            "Lunch",
            LocalDate.now()
        );

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(request)
            .when()
            .post("/api/expenses")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("지출 등록 API - 숫자 포함 설명으로 등록 시 정상 응답")
    void registerExpense_numberInDescription_success() {
        // given
        Category category = categoryRepository.findById(this.category.getId()).orElseThrow();
        RegisterExpenseRequest request = new RegisterExpenseRequest(
            category.getId().getId(),
            10000,
            "1st Meal",
            LocalDate.now()
        );

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(request)
            .when()
            .post("/api/expenses")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("지출 등록 API - 과거 날짜로 등록 시 정상 응답")
    void registerExpense_pastDate_success() {
        // given
        Category category = categoryRepository.findById(this.category.getId()).orElseThrow();
        RegisterExpenseRequest request = new RegisterExpenseRequest(
            category.getId().getId(),
            10000,
            "어제 점심",
            LocalDate.now().minusDays(30)
        );

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(request)
            .when()
            .post("/api/expenses")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("지출 등록 API - JSON 파싱 오류 시 실패")
    void registerExpense_invalidJson_failure() {
        // given - 잘못된 JSON 형식
        String invalidJson = "{\"consumptionGoalId\": \"" + categoryId.getId()
            + "\", \"amount\": 10000, \"description\": \"점심\", \"expenseAt\": \"2024-01-01\",}"; // trailing comma

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(invalidJson)
            .when()
            .post("/api/expenses")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", is("INVALID_INPUT"));
    }

    @Test
    @DisplayName("지출 등록 API - 필수 파라미터 누락 시 실패")
    void registerExpense_missingRequiredField_failure() {
        // given - consumptionGoalId 필드 누락
        String jsonWithoutCategoryId = "{\"amount\": 10000, \"description\": \"점심\", \"expenseAt\": \"2024-01-01\"}";

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(jsonWithoutCategoryId)
            .when()
            .post("/api/expenses")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", is("INVALID_INPUT"));
    }

    @Test
    @DisplayName("지출 등록 API - userId 파라미터 누락 시 실패")
    void registerExpense_missingUserId_failure() {
        // given
        RegisterExpenseRequest request = new RegisterExpenseRequest(
            categoryId.getId(), 10000, "점심", LocalDate.now().minusDays(1));

        // when & then
        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/api/expenses")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", is("INVALID_INPUT"));
    }

    @Test
    @DisplayName("지출 등록 API - 잘못된 userId 타입 시 실패")
    void registerExpense_invalidUserIdType_failure() {
        // given
        RegisterExpenseRequest request = new RegisterExpenseRequest(
            categoryId.getId(), 10000, "점심", LocalDate.now().minusDays(1));

        // when & then - userId를 문자열로 전달
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", "invalid")
            .body(request)
            .when()
            .post("/api/expenses")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", is("INVALID_INPUT"));
    }

    @Test
    @DisplayName("지출 등록 API - 잘못된 날짜 형식으로 등록 시 실패")
    void registerExpense_invalidDateFormat_failure() {
        // given - 잘못된 날짜 형식
        String jsonWithInvalidDate = "{\"consumptionGoalId\": \"" + categoryId.getId()
            + "\", \"amount\": 10000, \"description\": \"점심\", \"expenseAt\": \"2024-13-45\"}";

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(jsonWithInvalidDate)
            .when()
            .post("/api/expenses")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", is("INVALID_INPUT"));
    }

    @Test
    @DisplayName("지출 수정 API - JSON 파싱 오류 시 실패")
    void updateExpense_invalidJson_failure() {
        // given - 잘못된 JSON 형식
        String invalidJson =
            "{\"expenseId\": \"" + expenseId.getId() + "\", \"consumptionGoalId\": \""
                + categoryId.getId()
                + "\", \"expenseAt\": \"2024-01-01\",}"; // trailing comma

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(invalidJson)
            .when()
            .patch("/api/expenses/{expenseId}", expenseId.getId())
            .then()
            .statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
    }

    @Test
    @DisplayName("지출 수정 API - 날짜 형식이 잘못되었을 때 실패")
    void updateExpense_invalidDateFormat_failure() {
        // given
        String jsonWithInvalidDate =
            "{\"expenseId\": \"" + expenseId.getId() + "\", \"consumptionGoalId\": \""
                + categoryId.getId()
                + "\", \"expenseAt\": \"2024-02-30\"}";

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(jsonWithInvalidDate)
            .when()
            .patch("/api/expenses")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("지출 등록 API - null 값들로 등록 시 실패")
    void registerExpense_nullValues_failure() {
        // given - null 값들
        String jsonWithNulls = "{\"consumptionGoalId\": null, \"amount\": null, \"description\": null, \"expenseAt\": null}";

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .body(jsonWithNulls)
            .when()
            .post("/api/expenses")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", is("INVALID_INPUT"));
    }


    @Test
    @DisplayName("지출 삭제 API - 잘못된 expenseId 타입 시 실패")
    void deleteExpense_invalidExpenseIdType_failure() {
        // when & then - expenseId를 문자열로 전달
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .when()
            .delete("/api/expenses/invalid")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", is("INVALID_INPUT"));
    }

    @Test
    @DisplayName("지출 삭제 API - 음수 expenseId로 삭제 시 실패")
    void deleteExpense_negativeExpenseId_failure() {
        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId.getId())
            .when()
            .delete("/api/expenses/-1")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", is("INVALID_INPUT"));
    }
}