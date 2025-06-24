package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service;

import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoalId;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.exception.NotFoundConsumptionGoalException;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.repository.ConsumptionGoalRepository;
import com.prography.budgetbuddiesbackend.user.entity.UserId;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ConsumptionGoalServiceImpl implements ConsumptionGoalService {

    private final ConsumptionGoalRepository consumptionGoalRepository;

    @Override
    public ConsumptionGoal save(ConsumptionGoal goal) {
        return consumptionGoalRepository.save(goal);
    }

    @Override
    public List<ConsumptionGoal> getByUserAndYearMonth(UserId userId, YearMonth yearMonth) {
        if (yearMonth.isAfter(YearMonth.now())) {
            throw new NotFoundConsumptionGoalException();
        }

        return consumptionGoalRepository.findByUserIdAndGoalMonthWithCategory(userId, yearMonth);
    }

    @Override
    public List<ConsumptionGoal> getByIds(Collection<ConsumptionGoalId> ids) {
        List<ConsumptionGoal> result = consumptionGoalRepository.findAllById(ids);

        if (result.size() != ids.size()) {
            throw new NotFoundConsumptionGoalException();
        }

        return result;
    }
}