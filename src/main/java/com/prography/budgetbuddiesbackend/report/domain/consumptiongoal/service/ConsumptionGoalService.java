package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service;

import java.time.YearMonth;
import java.util.Collection;
import java.util.List;

import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.dto.BatchUpdateConsumptionGoalCapRequest.GoalCapUpdate;

public interface ConsumptionGoalService {
	/**
	 * 소비 목표를 저장합니다.
	 * @param goal 저장할 소비 목표
	 * @return 저장된 소비 목표
	 * @throws IllegalArgumentException 목표 금액이 0 이하인 경우
	 */
	ConsumptionGoal save(ConsumptionGoal goal);

	/**
	 * 사용자의 특정 월 소비 목표를 조회합니다.
	 * @param userId 사용자
	 * @param yearMonth 조회할 년월
	 * @return 소비 목표 목록
	 */
	List<ConsumptionGoal> getByUserAndYearMonth(Long userId, YearMonth yearMonth);

	List<ConsumptionGoal> findAllByIdList(Collection<Long> idList);
}
