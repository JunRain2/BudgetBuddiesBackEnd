package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity;

import com.prography.budgetbuddiesbackend.common.entity.BaseEntity;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.exception.NotUpdateConsumptionGoalException;
import com.prography.budgetbuddiesbackend.user.entity.UserId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.YearMonth;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "consumption_goal", schema = "budgetbuddies")
public class ConsumptionGoal extends BaseEntity {

    @EmbeddedId
    private ConsumptionGoalId id;

    @Embedded
    @NotNull
    @AttributeOverride(name = "id", column = @Column(name = "user_id", nullable = false))
    private UserId userId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotNull
    @Column(name = "cap", nullable = false)
    private Integer cap;

    @NotNull
    @Column(name = "goal_month", nullable = false, columnDefinition = "CHAR(7)")
    private YearMonth goalMonth;

    private ConsumptionGoal(
        ConsumptionGoalId consumptionGoalId,
        UserId userId,
        Category category,
        Integer cap,
        YearMonth goalMonth) {

        this.id = consumptionGoalId;
        this.userId = userId;
        this.category = category;
        this.cap = cap;
        this.goalMonth = goalMonth;
    }

    public static ConsumptionGoal of(UserId userId, Category category, Integer cap,
        YearMonth yearMonth) {
        return new ConsumptionGoal(ConsumptionGoalId.generate(), userId, category, cap, yearMonth);
    }

    public void canUpdate(UserId userId) {
        YearMonth now = YearMonth.now();

        if (!this.userId.equals(userId) || !now.equals(this.goalMonth)) {
            throw new NotUpdateConsumptionGoalException();
        }
    }

    public void update(Integer cap) {
        if (cap <= 0) {
            throw new NotUpdateConsumptionGoalException();
        }
        this.cap = cap;
    }
}