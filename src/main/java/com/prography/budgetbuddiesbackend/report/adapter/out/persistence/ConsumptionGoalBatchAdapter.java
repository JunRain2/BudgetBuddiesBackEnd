package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.ConsumptionGoalMigrationBatchPort;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
class ConsumptionGoalBatchAdapter implements ConsumptionGoalMigrationBatchPort {
	private final int CHUNK_SIZE = 100;

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final EntityManagerFactory entityManagerFactory;
	private final DataSource dataSource;

	@Bean
	public Job consumptionGoalJob() {
		return new JobBuilder("consumptionGoalJob", jobRepository).start(consumptionGoalStep()).build();
	}

	@Bean
	public Step consumptionGoalStep() {
		return new StepBuilder("consumptionGoalStep",
			jobRepository).<ConsumptionGoalEntity, ConsumptionGoalEntity>chunk(CHUNK_SIZE, transactionManager)
			.reader(reader())
			.processor(processor())
			.writer(writer())
			.build();
	}

	@Bean
	public JpaPagingItemReader<ConsumptionGoalEntity> reader() {
		LocalDate prevMonth = LocalDate.now().minusMonths(1).withDayOfMonth(1);
		LocalDate thisMonth = LocalDate.now().withDayOfMonth(1);

		Map<String, Object> params = new HashMap<>();
		params.put("prevMonth", prevMonth);
		params.put("thisMonth", thisMonth);

		return new JpaPagingItemReaderBuilder<ConsumptionGoalEntity>().name("consumptionGoalPagingReader")
			.entityManagerFactory(entityManagerFactory)
			.queryString("""
					SELECT c FROM ConsumptionGoalEntity c
					WHERE c.goalAt = :prevMonth
					AND NOT EXISTS (
						SELECT 1 FROM ConsumptionGoalEntity cc
						WHERE cc.userId = c.userId
						AND cc.categoryId = c.categoryId
						AND cc.goalAt = :thisMonth
					)
				""")
			.parameterValues(params)
			.pageSize(CHUNK_SIZE)
			.build();
	}

	@Bean
	public ItemProcessor<ConsumptionGoalEntity, ConsumptionGoalEntity> processor() {
		return ConsumptionGoalEntity::generateThisMonthConsumptionGoal;
	}

	@Bean
	public JdbcBatchItemWriter<ConsumptionGoalEntity> writer() {
		return new JdbcBatchItemWriterBuilder<ConsumptionGoalEntity>()
			.dataSource(dataSource)
			.sql("""
				INSERT INTO consumption_goal (user_id, category_id ,goal_at, cap)
				VALUES (?, ?, ?, ?)
				ON DUPLICATE KEY UPDATE cap = VALUES(cap)
				""")
			.itemPreparedStatementSetter((goal, ps) -> {
				ps.setLong(1, goal.getUserId());
				ps.setLong(2, goal.getCategoryId());
				ps.setObject(3, goal.getGoalAt());
				ps.setInt(4, goal.getCap());
			})
			.build();
	}

	@Override
	public void startMigration() {
		try {
			// 현재 시간을 기반으로 JobParameters 생성
			JobParameters jobParameters = new JobParametersBuilder()
				.addLong("timestamp", System.currentTimeMillis())
				.toJobParameters();

			// TaskExecutorJobLauncher 생성 및 설정
			TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
			jobLauncher.setJobRepository(jobRepository);
			jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
			jobLauncher.afterPropertiesSet();

			// 배치 작업 실행
			jobLauncher.run(consumptionGoalJob(), jobParameters);

			log.info("✅ Consumption goal batch job executed successfully.");
		} catch (Exception e) {
			log.error("❌ Failed to execute consumption goal batch job", e);
			throw new RuntimeException("Batch job execution failed", e);
		}
	}
}