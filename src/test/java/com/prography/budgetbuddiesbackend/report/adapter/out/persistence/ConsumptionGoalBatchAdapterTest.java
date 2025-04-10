package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.prography.budgetbuddiesbackend.util.TestSqlLoader;

@SpringBootTest
@Testcontainers
class ConsumptionGoalBatchAdapterTest {

	@Container
	@ServiceConnection
	static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8");

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job consumptionGoalJob;

	@Autowired
	private DataSource dataSource;

	@Test
	void 소비목표_생성_배치가_정상적으로_완료된다() throws Exception {
		TestSqlLoader.executeSql(dataSource, "consumption_goal_batch_test_init.sql");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		Integer expectedCount = jdbcTemplate.queryForObject(
			"""
				SELECT COUNT(*) FROM consumption_goal
				""",
			Integer.class
		);

		// given
		JobParameters jobParameters = new JobParametersBuilder()
			.addLong("timestamp", System.currentTimeMillis())
			.toJobParameters();

		// when
		JobExecution jobExecution = jobLauncher.run(consumptionGoalJob, jobParameters);

		// then
		Integer resultCount = jdbcTemplate.queryForObject(
			"SELECT COUNT(*) FROM consumption_goal WHERE goal_at = DATE_FORMAT(CURDATE(), '%Y-%m-01')",
			Integer.class
		);

		assertThat(resultCount).isEqualTo(expectedCount);
		assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
	}
}