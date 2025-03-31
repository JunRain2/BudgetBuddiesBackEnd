package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.QueryConsumptionGoalResult;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.CreateConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.FindConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.domain.ConsumptionGoal;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
class ConsumptionGoalPersistenceAdapter implements CreateConsumptionGoalPort, FindConsumptionGoalPort {

	private final ConsumptionGoalRepository consumptionGoalRepository;
	private final ConsumptionGoalQueryRepository queryConsumptionGoalRepository;
	private final ConsumptionGoalMapper mapper;

	@Override
	public void createConsumptionGoal(Long userId, ConsumptionGoal consumptionGoal) {
		ConsumptionGoalEntity consumptionGoalEntity = mapper.consumptionGoalToEntity(consumptionGoal, userId);
		consumptionGoalRepository.save(consumptionGoalEntity);
	}

	@Override
	public List<QueryConsumptionGoalResult> findMonthlyUserConsumptionGoalList(LocalDate goalAt, Long userId) {
		return queryConsumptionGoalRepository.findMonthlyUserConsumptionGoalList(goalAt, userId);
	}

	@Override
	public Map<Long, ConsumptionGoal> findThisMonthUserConsumptionGoalMap(Long userId,
		List<Long> consumptionGoalIdList) {
		List<ConsumptionGoalEntity> consumptionGoalEntities = consumptionGoalRepository.findAllByUserIdAndIdIn(userId,
			consumptionGoalIdList);

		return consumptionGoalEntities.stream().collect(
			Collectors.toMap(ConsumptionGoalEntity::getId, mapper::entityToConsumptionGoal)
		);
	}
}
