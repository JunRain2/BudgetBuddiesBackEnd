package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.YearMonth;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.exception.NotFoundConsumptionGoalException;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.repository.ConsumptionGoalRepository;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;

@ExtendWith(MockitoExtension.class)
class ConsumptionGoalDomainServiceTest {
	@InjectMocks
	private ConsumptionGoalServiceImpl consumptionGoalService;

	@Mock
	private ConsumptionGoalRepository consumptionGoalRepository;

	private User user;
	private Category category;
	private ConsumptionGoal consumptionGoal;

	@BeforeEach
	void setUp() {
		user = User.of();
		category = Category.of(user, "식비");
		consumptionGoal = ConsumptionGoal.of(user, category, 100000, YearMonth.now());
	}

	@Test
	@DisplayName("소비 목표를 저장한다")
	void save_정상동작() {
		// given
		when(consumptionGoalRepository.save(any(ConsumptionGoal.class))).thenReturn(consumptionGoal);

		// when
		ConsumptionGoal savedGoal = consumptionGoalService.save(consumptionGoal);

		// then
		assertThat(savedGoal).isEqualTo(consumptionGoal);
		verify(consumptionGoalRepository).save(consumptionGoal);
	}

	@Test
	@DisplayName("사용자와 연월로 소비 목표를 조회한다")
	void getByUserAndYearMonth_정상동작() {
		// given
		YearMonth yearMonth = YearMonth.now();
		List<ConsumptionGoal> goals = List.of(consumptionGoal);
		when(consumptionGoalRepository.findByUserAndGoalMonthWithCategory(user, yearMonth)).thenReturn(goals);

		// when
		List<ConsumptionGoal> foundGoals = consumptionGoalService.getByUserAndYearMonth(user, yearMonth);

		// then
		assertThat(foundGoals).isEqualTo(goals);
		verify(consumptionGoalRepository).findByUserAndGoalMonthWithCategory(user, yearMonth);
	}

	@Test
	@DisplayName("미래 연월로 조회시 예외가 발생한다")
	void getByUserAndYearMonth_미래연월_예외() {
		// given
		YearMonth futureMonth = YearMonth.now().plusMonths(1);

		// when & then
		assertThatThrownBy(() -> consumptionGoalService.getByUserAndYearMonth(user, futureMonth)).isInstanceOf(
			NotFoundConsumptionGoalException.class);
		verify(consumptionGoalRepository, never()).findByUserAndGoalMonthWithCategory(any(), any());
	}

	@Test
	@DisplayName("소비 목표가 없는 경우 빈 리스트를 반환한다")
	void getByUserAndYearMonth_결과없음() {
		// given
		YearMonth yearMonth = YearMonth.now();
		when(consumptionGoalRepository.findByUserAndGoalMonthWithCategory(user, yearMonth)).thenReturn(List.of());

		// when
		List<ConsumptionGoal> foundGoals = consumptionGoalService.getByUserAndYearMonth(user, yearMonth);

		// then
		assertThat(foundGoals).isEmpty();
		verify(consumptionGoalRepository).findByUserAndGoalMonthWithCategory(user, yearMonth);
	}
} 