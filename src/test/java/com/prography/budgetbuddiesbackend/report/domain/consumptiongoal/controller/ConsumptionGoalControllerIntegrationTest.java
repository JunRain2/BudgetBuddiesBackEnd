package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.prography.budgetbuddiesbackend.common.AbstractControllerTest;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryId;
import com.prography.budgetbuddiesbackend.report.domain.category.repository.CategoryRepository;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.controller.dto.BatchUpdateConsumptionGoalCapRequest;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.controller.dto.BatchUpdateConsumptionGoalCapRequest.GoalCapUpdate;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.repository.ConsumptionGoalRepository;
import com.prography.budgetbuddiesbackend.user.entity.User;
import com.prography.budgetbuddiesbackend.user.repository.UserRepository;
import io.restassured.http.ContentType;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

class ConsumptionGoalControllerIntegrationTest extends AbstractControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ConsumptionGoalRepository consumptionGoalRepository;

    private UUID userId;
    private UUID categoryId;
    private User savedUser;
    private Category savedCategory;

    @BeforeEach
    void setUp() {
        User user = User.of();
        savedUser = userRepository.save(user);
        userId = savedUser.getId().getId();

        Category category = Category.of(savedUser.getId(), "테스트카테고리");
        savedCategory = categoryRepository.save(category);
        categoryId = savedCategory.getId().getId();
    }

    @Test
    @DisplayName("월별 소비목표 조회 API - 정상 응답")
    void getUserConsumptionGoalsByMonth_success() {
        // given
        String yearMonth = "2024-06";
        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("yearMonth", yearMonth)
            .queryParam("userId", userId)
            .when()
            .get("/api/consumption-goals")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."))
            .body("data", notNullValue());
    }

    @Test
    @DisplayName("월별 소비목표 조회 API - 소비목표가 있는 경우")
    void getUserConsumptionGoalsByMonth_withGoals() {
        // given
        String yearMonth = "2024-06";
        YearMonth yearMonthObj = YearMonth.parse(yearMonth);

        // 소비목표 생성
        Category category = categoryRepository.findById(CategoryId.of(categoryId)).orElseThrow();
        ConsumptionGoal goal = ConsumptionGoal.of(savedUser.getId(), category, 100000,
            yearMonthObj);
        consumptionGoalRepository.save(goal);

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("yearMonth", yearMonth)
            .queryParam("userId", userId)
            .when()
            .get("/api/consumption-goals")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."))
            .body("data", notNullValue())
            .body("data.size()", greaterThan(0));
    }

    @Test
    @DisplayName("월별 소비목표 조회 API - 미래 연월 조회 시 실패")
    void getUserConsumptionGoalsByMonth_futureMonth_success() {
        // given
        String futureYearMonth = YearMonth.now().plusMonths(1).toString();
        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("yearMonth", futureYearMonth)
            .queryParam("userId", userId)
            .when()
            .get("/api/consumption-goals")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("월별 소비목표 조회 API - 잘못된 연월 형식으로 조회 시 실패")
    void getUserConsumptionGoalsByMonth_invalidFormat_failure() {
        // given
        String invalidYearMonth = "2024-13"; // 잘못된 월
        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("yearMonth", invalidYearMonth)
            .queryParam("userId", userId)
            .when()
            .get("/api/consumption-goals")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", is("INVALID_INPUT"));
    }

    @Test
    @DisplayName("월별 소비목표 조회 API - 과거 연월 조회 시 정상 응답")
    void getUserConsumptionGoalsByMonth_pastMonth_success() {
        // given
        String pastYearMonth = YearMonth.now().minusMonths(1).toString();
        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("yearMonth", pastYearMonth)
            .queryParam("userId", userId)
            .when()
            .get("/api/consumption-goals")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."))
            .body("data", notNullValue());
    }

    @Test
    @DisplayName("소비목표 cap 일괄 수정 API - 정상 응답")
    void batchUpdateConsumptionGoalCap_success() {
        // given
        YearMonth yearMonth = YearMonth.now();
        Category category = categoryRepository.findById(CategoryId.of(categoryId)).orElseThrow();

        // 다른 카테고리 생성
        Category category2 = Category.of(savedUser.getId(), "테스트카테고리2");
        Category savedCategory2 = categoryRepository.save(category2);

        // 서로 다른 카테고리로 소비목표 생성
        ConsumptionGoal goal1 = ConsumptionGoal.of(savedUser.getId(), category, 100000, yearMonth);
        ConsumptionGoal goal2 = ConsumptionGoal.of(savedUser.getId(), savedCategory2, 200000,
            yearMonth);
        ConsumptionGoal savedGoal1 = consumptionGoalRepository.save(goal1);
        ConsumptionGoal savedGoal2 = consumptionGoalRepository.save(goal2);

        List<GoalCapUpdate> updates = List.of(
            new GoalCapUpdate(savedGoal1.getId().getId(), 150000),
            new GoalCapUpdate(savedGoal2.getId().getId(), 250000)
        );
        BatchUpdateConsumptionGoalCapRequest request = new BatchUpdateConsumptionGoalCapRequest(
            updates);

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId)
            .body(request)
            .when()
            .patch("/api/consumption-goals/batch-cap")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("소비목표 cap 일괄 수정 API - 음수 cap으로 수정 시 실패")
    void batchUpdateConsumptionGoalCap_negativeCap_failure() {
        // given
        YearMonth yearMonth = YearMonth.now();
        Category category = categoryRepository.findById(CategoryId.of(categoryId)).orElseThrow();

        // 소비목표 생성
        ConsumptionGoal goal = ConsumptionGoal.of(savedUser.getId(), category, 100000, yearMonth);
        ConsumptionGoal savedGoal = consumptionGoalRepository.save(goal);

        List<GoalCapUpdate> updates = List.of(
            new GoalCapUpdate(savedGoal.getCategory().getId().getId(), -1000)
        );
        BatchUpdateConsumptionGoalCapRequest request = new BatchUpdateConsumptionGoalCapRequest(
            updates);

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId)
            .body(request)
            .when()
            .patch("/api/consumption-goals/batch-cap")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", is("INVALID_INPUT"));
    }

    @Test
    @DisplayName("소비목표 cap 일괄 수정 API - 0원 cap으로 수정 시 실패")
    void batchUpdateConsumptionGoalCap_zeroCap_failure() {
        // given
        YearMonth yearMonth = YearMonth.now();
        Category category = categoryRepository.findById(CategoryId.of(categoryId)).orElseThrow();

        // 소비목표 생성
        ConsumptionGoal goal = ConsumptionGoal.of(savedUser.getId(), category, 100000, yearMonth);
        ConsumptionGoal savedGoal = consumptionGoalRepository.save(goal);

        List<GoalCapUpdate> updates = List.of(
            new GoalCapUpdate(savedGoal.getCategory().getId().getId(), 0)
        );
        BatchUpdateConsumptionGoalCapRequest request = new BatchUpdateConsumptionGoalCapRequest(
            updates);

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId)
            .body(request)
            .when()
            .patch("/api/consumption-goals/batch-cap")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", is("INVALID_INPUT"));
    }

    @Test
    @DisplayName("소비목표 cap 일괄 수정 API - 빈 리스트로 수정 시 실패")
    void batchUpdateConsumptionGoalCap_emptyList_failure() {
        // given
        List<GoalCapUpdate> updates = List.of();
        BatchUpdateConsumptionGoalCapRequest request = new BatchUpdateConsumptionGoalCapRequest(
            updates);

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId)
            .body(request)
            .when()
            .patch("/api/consumption-goals/batch-cap")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", is("INVALID_INPUT"));
    }

    @Test
    @DisplayName("소비목표 cap 일괄 수정 API - 경계값 테스트 (1원)")
    void batchUpdateConsumptionGoalCap_oneWon_success() {
        // given
        YearMonth yearMonth = YearMonth.now();
        Category category = categoryRepository.findById(CategoryId.of(categoryId)).orElseThrow();

        // 소비목표 생성
        ConsumptionGoal goal = ConsumptionGoal.of(savedUser.getId(), category, 100000, yearMonth);
        ConsumptionGoal savedGoal = consumptionGoalRepository.save(goal);

        List<GoalCapUpdate> updates = List.of(
            new GoalCapUpdate(savedGoal.getId().getId(), 1)
        );
        BatchUpdateConsumptionGoalCapRequest request = new BatchUpdateConsumptionGoalCapRequest(
            updates);

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId)
            .body(request)
            .when()
            .patch("/api/consumption-goals/batch-cap")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("소비목표 cap 일괄 수정 API - 경계값 테스트 (최대 금액)")
    void batchUpdateConsumptionGoalCap_maxAmount_success() {
        // given
        YearMonth yearMonth = YearMonth.now();
        Category category = categoryRepository.findById(CategoryId.of(categoryId)).orElseThrow();

        // 소비목표 생성
        ConsumptionGoal goal = ConsumptionGoal.of(savedUser.getId(), category, 100000, yearMonth);
        ConsumptionGoal savedGoal = consumptionGoalRepository.save(goal);

        List<GoalCapUpdate> updates = List.of(
            new GoalCapUpdate(savedGoal.getId().getId(), Integer.MAX_VALUE)
        );
        BatchUpdateConsumptionGoalCapRequest request = new BatchUpdateConsumptionGoalCapRequest(
            updates);

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId)
            .body(request)
            .when()
            .patch("/api/consumption-goals/batch-cap")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("월별 소비목표 조회 API - 다른 사용자의 소비목표는 조회되지 않음")
    void getUserConsumptionGoalsByMonth_otherUserIsolation() {
        // given
        // 다른 사용자 생성
        User otherUser = User.of();
        User savedOtherUser = userRepository.save(otherUser);

        // 다른 사용자의 카테고리 생성
        Category otherCategory = Category.of(savedOtherUser.getId(), "다른사용자카테고리");
        Category savedOtherCategory = categoryRepository.save(otherCategory);

        // 다른 사용자의 소비목표 생성
        YearMonth yearMonth = YearMonth.now();
        ConsumptionGoal otherGoal = ConsumptionGoal.of(savedOtherUser.getId(), savedOtherCategory,
            100000, yearMonth);
        consumptionGoalRepository.save(otherGoal);

        // when & then - 원래 사용자로 조회 시 다른 사용자의 소비목표는 보이지 않아야 함
        given()
            .contentType(ContentType.JSON)
            .queryParam("yearMonth", yearMonth.toString())
            .queryParam("userId", userId)
            .when()
            .get("/api/consumption-goals")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."))
            .body("data", empty());
    }

    @Test
    @DisplayName("소비목표 cap 일괄 수정 API - 다른 사용자의 소비목표 수정 시 실패")
    void batchUpdateConsumptionGoalCap_otherUserGoal_failure() {
        // given
        // 다른 사용자 생성
        User otherUser = User.of();
        User savedOtherUser = userRepository.save(otherUser);

        // 다른 사용자의 카테고리 생성
        Category otherCategory = Category.of(savedOtherUser.getId(), "다른사용자카테고리");
        Category savedOtherCategory = categoryRepository.save(otherCategory);

        // 다른 사용자의 소비목표 생성
        YearMonth yearMonth = YearMonth.now();
        ConsumptionGoal otherGoal = ConsumptionGoal.of(savedOtherUser.getId(), savedOtherCategory,
            100000, yearMonth);
        ConsumptionGoal savedOtherGoal = consumptionGoalRepository.save(otherGoal);

        List<GoalCapUpdate> updates = List.of(
            new GoalCapUpdate(savedOtherGoal.getId().getId(), 200000)
        );
        BatchUpdateConsumptionGoalCapRequest request = new BatchUpdateConsumptionGoalCapRequest(
            updates);

        // when & then - 원래 사용자로 다른 사용자의 소비목표 수정 시도
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId)
            .body(request)
            .when()
            .patch("/api/consumption-goals/batch-cap")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", is("INVALID_INPUT"));
    }

    @Test
    @DisplayName("월별 소비목표 조회 API - 현재 연월 조회 시 정상 응답")
    void getUserConsumptionGoalsByMonth_currentMonth_success() {
        // given
        String currentYearMonth = YearMonth.now().toString();
        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("yearMonth", currentYearMonth)
            .queryParam("userId", userId)
            .when()
            .get("/api/consumption-goals")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."))
            .body("data", notNullValue());
    }

    @Test
    @DisplayName("월별 소비목표 조회 API - 1년 전 연월 조회 시 정상 응답")
    void getUserConsumptionGoalsByMonth_oneYearAgo_success() {
        // given
        String oneYearAgoYearMonth = YearMonth.now().minusYears(1).toString();
        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("yearMonth", oneYearAgoYearMonth)
            .queryParam("userId", userId)
            .when()
            .get("/api/consumption-goals")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."))
            .body("data", notNullValue());
    }

    @Test
    @DisplayName("소비목표 cap 일괄 수정 API - 여러 소비목표 동시 수정 시 정상 응답")
    void batchUpdateConsumptionGoalCap_multipleGoals_success() {
        // given
        YearMonth yearMonth = YearMonth.now();
        Category category = categoryRepository.findById(CategoryId.of(categoryId)).orElseThrow();

        // 다른 카테고리들 생성
        Category category2 = Category.of(savedUser.getId(), "테스트카테고리2");
        Category category3 = Category.of(savedUser.getId(), "테스트카테고리3");
        Category savedCategory2 = categoryRepository.save(category2);
        Category savedCategory3 = categoryRepository.save(category3);

        // 서로 다른 카테고리로 여러 소비목표 생성
        ConsumptionGoal goal1 = ConsumptionGoal.of(savedUser.getId(), category, 100000, yearMonth);
        ConsumptionGoal goal2 = ConsumptionGoal.of(savedUser.getId(), savedCategory2, 200000,
            yearMonth);
        ConsumptionGoal goal3 = ConsumptionGoal.of(savedUser.getId(), savedCategory3, 300000,
            yearMonth);
        ConsumptionGoal savedGoal1 = consumptionGoalRepository.save(goal1);
        ConsumptionGoal savedGoal2 = consumptionGoalRepository.save(goal2);
        ConsumptionGoal savedGoal3 = consumptionGoalRepository.save(goal3);

        List<GoalCapUpdate> updates = List.of(
            new GoalCapUpdate(savedGoal1.getId().getId(), 150000),
            new GoalCapUpdate(savedGoal2.getId().getId(), 250000),
            new GoalCapUpdate(savedGoal3.getId().getId(), 350000)
        );
        BatchUpdateConsumptionGoalCapRequest request = new BatchUpdateConsumptionGoalCapRequest(
            updates);

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId)
            .body(request)
            .when()
            .patch("/api/consumption-goals/batch-cap")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."));
    }

    @Test
    @DisplayName("월별 소비목표 조회 API - yearMonth 파라미터 누락 시 실패")
    void getUserConsumptionGoalsByMonth_missingYearMonth_failure() {
        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId)
            .when()
            .get("/api/consumption-goals")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("소비목표 cap 일괄 수정 API - 존재하지 않는 소비목표 ID 포함 시 실패")
    void batchUpdateConsumptionGoals_categoryNotFound_failure() {
        // given
        BatchUpdateConsumptionGoalCapRequest.GoalCapUpdate validDto = new BatchUpdateConsumptionGoalCapRequest.GoalCapUpdate(
            categoryId, 200000);
        BatchUpdateConsumptionGoalCapRequest.GoalCapUpdate invalidDto = new BatchUpdateConsumptionGoalCapRequest.GoalCapUpdate(
            UUID.randomUUID(), 150000);
        BatchUpdateConsumptionGoalCapRequest request = new BatchUpdateConsumptionGoalCapRequest(
            List.of(validDto, invalidDto));

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId)
            .body(request)
            .when()
            .patch("/api/consumption-goals/batch-cap")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .body("code", is("NOT_FOUND"));
    }

    @Test
    @DisplayName("소비목표 cap 일괄 수정 API - 다른 사용자의 소비목표 ID 포함 시 실패")
    void batchUpdateConsumptionGoals_otherUserCategory_failure() {
        // given
        User otherUser = userRepository.save(User.of());
        Category otherCategory = Category.of(otherUser.getId(), "다른유저카테고리");
        categoryRepository.save(otherCategory);

        ConsumptionGoal userConsumptionGoal = ConsumptionGoal.of(savedUser.getId(), savedCategory, 200000, YearMonth.now());
        ConsumptionGoal otherUserConsumptionGoal = ConsumptionGoal.of(otherUser.getId(), otherCategory, 200000, YearMonth.now());

        consumptionGoalRepository.saveAll(List.of(userConsumptionGoal, otherUserConsumptionGoal));

        BatchUpdateConsumptionGoalCapRequest.GoalCapUpdate myDto = new BatchUpdateConsumptionGoalCapRequest.GoalCapUpdate(
            userConsumptionGoal.getUserId().getId(), 200000);
        BatchUpdateConsumptionGoalCapRequest.GoalCapUpdate otherDto = new BatchUpdateConsumptionGoalCapRequest.GoalCapUpdate(
            otherUserConsumptionGoal.getId().getId(), 150000);
        BatchUpdateConsumptionGoalCapRequest request = new BatchUpdateConsumptionGoalCapRequest(
            List.of(myDto, otherDto));

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId)
            .body(request)
            .when()
            .patch("/api/consumption-goals/batch-cap")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .body("code", is("NOT_FOUND"));
    }

    @Test
    @DisplayName("소비목표 cap 일괄 수정 API - 금액이 음수일 때 실패")
    void batchUpdateConsumptionGoals_negativeAmount_failure() {
        // given
        BatchUpdateConsumptionGoalCapRequest.GoalCapUpdate invalidDto = new BatchUpdateConsumptionGoalCapRequest.GoalCapUpdate(
            categoryId, -100);
        BatchUpdateConsumptionGoalCapRequest request = new BatchUpdateConsumptionGoalCapRequest(
            List.of(invalidDto));

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId)
            .body(request)
            .when()
            .patch("/api/consumption-goals/batch-cap")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("code", is("INVALID_INPUT"));
    }

    @Test
    @DisplayName("사용자의 특정 년월 소비 목표 조회 - 정상 응답")
    void getUserConsumptionGoals_success() {
        // given
        ConsumptionGoal goal = ConsumptionGoal.of(savedUser.getId(), savedCategory, 250000,
            YearMonth.now());
        consumptionGoalRepository.save(goal);

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId)
            .queryParam("yearMonth", YearMonth.now().toString())
            .when()
            .get("/api/consumption-goals")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."))
            .body("data", notNullValue());
    }

    @Test
    @DisplayName("사용자의 특정 년월 소비 목표 조회 - 소비 목표가 없을 때 빈 리스트 응답")
    void getUserConsumptionGoals_emptyList() {
        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId)
            .queryParam("yearMonth", YearMonth.now().toString())
            .when()
            .get("/api/consumption-goals")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."))
            .body("data", empty());
    }

    @Test
    @DisplayName("사용자의 특정 년월 소비 목표 조회 - 다른 사용자의 소비 목표는 조회되지 않음")
    void getUserConsumptionGoals_otherUserIsolation() {
        // given
        User otherUser = userRepository.save(User.of());
        Category otherCategory = Category.of(otherUser.getId(), "다른유저카테고리");
        categoryRepository.save(otherCategory);
        ConsumptionGoal otherGoal = ConsumptionGoal.of(otherUser.getId(), otherCategory, 150000,
            YearMonth.now());
        consumptionGoalRepository.save(otherGoal);

        // when & then
        given()
            .contentType(ContentType.JSON)
            .queryParam("userId", userId)
            .queryParam("yearMonth", YearMonth.now().toString())
            .when()
            .get("/api/consumption-goals")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", is("SUCCESS"))
            .body("message", is("요청이 성공했습니다."))
            .body("data", empty());
    }
}