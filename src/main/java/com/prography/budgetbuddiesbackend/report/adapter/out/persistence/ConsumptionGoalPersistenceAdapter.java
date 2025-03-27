package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.prography.budgetbuddiesbackend.report.adapter.out.persistence.exception.NotFoundUserException;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.CreateConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.FindConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.domain.ConsumptionGoal;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ConsumptionGoalPersistenceAdapter implements CreateConsumptionGoalPort, FindConsumptionGoalPort {

	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;
	private final ExpenseRepository expenseRepository;
	private final PreviousConsumptionGoalRepository previousConsumptionGoalRepository;

	private final ConsumptionGoalMapper mapper;

	@Override
	public void createConsumptionGoal(Long userId, ConsumptionGoal consumptionGoal) {
		UserEntity userEntity = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
		CategoryEntity categoryEntity = mapper.consumptionGoalToCategoryEntity(userEntity, consumptionGoal);

		categoryRepository.save(categoryEntity);
	}

	@Override
	public List<ConsumptionGoal> findMonthlyUserConsumptionGoalList(LocalDate goalAt, Long userId) {
		UserEntity userEntity = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);

		LocalDate now = LocalDate.now();
		now = now.withDayOfMonth(1);

		if (now.equals(goalAt)) {
			return getConsumptionGoalsByCategoryEntity(userEntity);
		}

		return getConsumptionGoalsByPreviousConsumptionGoalEntity(now, userEntity);
	}

	private List<ConsumptionGoal> getConsumptionGoalsByCategoryEntity(UserEntity userEntity) {
		List<CategoryEntity> categoryEntityList = categoryRepository.findAllByUser(userEntity);
		return categoryEntityList.stream().map(c -> {
			int spendingMoney = expenseRepository.sumAmountByExpenseAtAndCategory(LocalDate.now(), c);
			return mapper.categoryEntityToConsumptionGoal(c, spendingMoney);
		}).toList();
	}

	private List<ConsumptionGoal> getConsumptionGoalsByPreviousConsumptionGoalEntity(LocalDate now,
		UserEntity userEntity) {
		List<PreviousConsumptionGoalEntity> previousConsumptionGoalEntityList = previousConsumptionGoalRepository.findByGoalAtAndUser(
			now, userEntity);

		return previousConsumptionGoalEntityList.stream()
			.map(mapper::previousConsumptionGoalEntityToConsumptionGoal)
			.toList();
	}
}
