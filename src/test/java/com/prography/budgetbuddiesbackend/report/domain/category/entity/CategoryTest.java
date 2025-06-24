package com.prography.budgetbuddiesbackend.report.domain.category.entity;

import static org.assertj.core.api.Assertions.*;

import java.time.YearMonth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.prography.budgetbuddiesbackend.report.domain.category.exception.UnmodifiableCategoryException;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;
import com.prography.budgetbuddiesbackend.user.entity.UserId;

class CategoryTest {

	@Test
	@DisplayName("userId로 카테고리를 생성할 수 있다")
	void createCategoryWithUserId() {
		// given
		UserId userId = UserId.generate();
		String name = "식비";

		// when
		Category category = Category.of(userId, name);

		// then
		assertThat(category.getUserId()).isEqualTo(userId);
		assertThat(category.getName()).isEqualTo(name);
		assertThat(category.getType()).isEqualTo(CategoryType.CUSTOM);
	}

	@Test
	@DisplayName("카테고리 소유자 검증이 성공한다")
	void validateModifiable_Success() {
		// given
		UserId userId = UserId.generate();
		Category category = Category.of(userId, "식비");

		// when & then
		assertThatCode(() -> category.validateModifiable(userId))
			.doesNotThrowAnyException();
	}

	@Test
	@DisplayName("다른 사용자가 카테고리를 수정하려고 하면 예외가 발생한다")
	void validateModifiable_OtherUser_ThrowsException() {
		// given
		UserId ownerId = UserId.generate();
		UserId otherUserId = UserId.generate();
		Category category = Category.of(ownerId, "식비");

		// when & then
		assertThatThrownBy(() -> category.validateModifiable(otherUserId))
			.isInstanceOf(UnmodifiableCategoryException.class);
	}

	@Test
	@DisplayName("기본 카테고리는 수정할 수 없다")
	void validateModifiable_DefaultCategory_ThrowsException() {
		// given
		UserId userId = UserId.generate();
		Category category = Category.of(userId, "기본카테고리");
		// 기본 카테고리로 설정 (리플렉션 사용)
		try {
			java.lang.reflect.Field typeField = Category.class.getDeclaredField("type");
			typeField.setAccessible(true);
			typeField.set(category, CategoryType.DEFAULT);
		} catch (Exception e) {
			fail("리플렉션 설정 실패");
		}

		// when & then
		assertThatThrownBy(() -> category.validateModifiable(userId))
			.isInstanceOf(UnmodifiableCategoryException.class);
	}

	@Test
	@DisplayName("초기 소비 목표를 생성할 수 있다")
	void createInitialGoal() {
		// given
		UserId userId = UserId.generate();
		Category category = Category.of(userId, "식비");
		YearMonth yearMonth = YearMonth.of(2024, 6);

		// when
		ConsumptionGoal goal = category.createInitialGoal(yearMonth);

		// then
		assertThat(goal.getUserId()).isEqualTo(userId);
		assertThat(goal.getCategory()).isEqualTo(category);
		assertThat(goal.getCap()).isEqualTo(200000);
		assertThat(goal.getGoalMonth()).isEqualTo(yearMonth);
	}
} 