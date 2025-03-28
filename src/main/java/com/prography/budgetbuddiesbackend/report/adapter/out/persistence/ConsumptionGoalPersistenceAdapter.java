package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.prography.budgetbuddiesbackend.report.adapter.out.persistence.exception.NotFoundCategoryException;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.CreateConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.FindConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.UpdateConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.domain.ConsumptionGoal;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ConsumptionGoalPersistenceAdapter implements CreateConsumptionGoalPort, FindConsumptionGoalPort,
	UpdateConsumptionGoalPort {

	private final CategoryRepository categoryRepository;
	private final ExpenseRepository expenseRepository;
	private final PreviousConsumptionGoalRepository previousConsumptionGoalRepository;

	private final ConsumptionGoalMapper mapper;

	@Override
	public void createConsumptionGoal(Long userId, ConsumptionGoal consumptionGoal) {
		CategoryEntity categoryEntity = mapper.consumptionGoalToCategoryEntity(userId, consumptionGoal);

		categoryRepository.save(categoryEntity);
	}

	@Override
	public List<ConsumptionGoal> findMonthlyUserConsumptionGoalList(LocalDate goalAt, Long userId) {
		LocalDate now = LocalDate.now();
		now = now.withDayOfMonth(1);

		if (now.equals(goalAt)) {
			return getConsumptionGoalsByCategoryEntity(userId);
		}

		return getConsumptionGoalsByPreviousConsumptionGoalEntity(now, userId);
	}

	private List<ConsumptionGoal> getConsumptionGoalsByCategoryEntity(Long userId) {
		List<CategoryEntity> categoryEntityList = categoryRepository.findAllByUserId(userId);

		return categoryEntityList.stream().map(c -> {
			int spendingMoney = expenseRepository.sumAmountByExpenseAtAndCategory(LocalDate.now(), c.getId());
			return mapper.categoryEntityToConsumptionGoal(c, spendingMoney);
		}).toList();
	}

	private List<ConsumptionGoal> getConsumptionGoalsByPreviousConsumptionGoalEntity(LocalDate now, Long userId) {
		List<PreviousConsumptionGoalEntity> previousConsumptionGoalEntityList = previousConsumptionGoalRepository.findByGoalAtAndUserId(
			now, userId);

		return previousConsumptionGoalEntityList.stream()
			.map(mapper::previousConsumptionGoalEntityToConsumptionGoal)
			.toList();
	}

	@Override
	public Map<Long, ConsumptionGoal> findThisMonthUserConsumptionGoalMap(Long userId,
		List<Long> consumptionGoalIdList) {

		List<CategoryEntity> categoryEntityList = categoryRepository.findALlByUserIdAndIdIn(userId,
			consumptionGoalIdList);

		return categoryEntityList.stream()
			.collect(Collectors.toMap(CategoryEntity::getId,
				c -> mapper.categoryEntityToConsumptionGoal(c,
					expenseRepository.sumAmountByExpenseAtAndCategory(LocalDate.now(), c.getId()))));
	}

	@Override
	public void updateConsumptionGoalListCap(List<ConsumptionGoal> consumptionGoalList) {
		for (ConsumptionGoal consumptionGoal : consumptionGoalList) {
			CategoryEntity categoryEntity = categoryRepository.findById(consumptionGoal.getConsumptionGoalId())
				.orElseThrow(
					NotFoundCategoryException::new);

			categoryEntity.modifyCap(consumptionGoal.getCap().value());
			categoryRepository.save(categoryEntity);
		}
	}
}
