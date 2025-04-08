package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.CreateConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.FindConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.UpdateConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.domain.ConsumptionGoal;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
class ConsumptionGoalPersistenceAdapter implements CreateConsumptionGoalPort, FindConsumptionGoalPort,
	UpdateConsumptionGoalPort {

	private final ConsumptionGoalRepository consumptionGoalRepository;
	private final ConsumptionGoalMapper mapper;

	@Override
	public void createConsumptionGoal(Long userId, ConsumptionGoal consumptionGoal) {
		ConsumptionGoalEntity consumptionGoalEntity = mapper.consumptionGoalToEntity(consumptionGoal, userId);
		consumptionGoalRepository.save(consumptionGoalEntity);
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

	@Override
	public void updateConsumptionGoalListCap(List<ConsumptionGoal> consumptionGoalList) {
		Map<Long, ConsumptionGoal> consumptionGoalMap = consumptionGoalList.stream().collect(
			Collectors.toMap(ConsumptionGoal::getConsumptionGoalId, Function.identity())
		);

		List<ConsumptionGoalEntity> consumptionGoalEntityList = consumptionGoalRepository.findAllByIdIn(
			consumptionGoalMap.keySet().stream().toList());
		consumptionGoalEntityList.forEach(
			c -> {
				ConsumptionGoal consumptionGoal = consumptionGoalMap.get(c.getId());
				c.updateCap(consumptionGoal.getCap().value());
			}
		);

		consumptionGoalRepository.saveAll(consumptionGoalEntityList);
	}
}
