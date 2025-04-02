package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

interface ConsumptionGoalRepository extends JpaRepository<ConsumptionGoalEntity, Long> {

	List<ConsumptionGoalEntity> findAllByUserIdAndIdIn(Long userId, Collection<Long> ids);

	List<ConsumptionGoalEntity> findAllByIdIn(List<Long> ids);
}
