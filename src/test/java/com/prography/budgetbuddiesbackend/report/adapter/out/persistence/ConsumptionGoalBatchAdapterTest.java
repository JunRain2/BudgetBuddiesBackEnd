package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger log = LoggerFactory.getLogger(ConsumptionGoalBatchAdapterTest.class);

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
		// 더미 데이터 setup
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		TestSqlLoader.executeProcedure(dataSource, "consumption_goal_batch_procedure.sql");
		jdbcTemplate.execute("CALL insert_dummy_data();");

		Integer expectedCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM consumption_goal", Integer.class);

		JobParameters jobParameters = new JobParametersBuilder().addLong("timestamp", System.currentTimeMillis())
			.toJobParameters();

		log.info("[BatchJob] 소비목표 배치 실행 시작");
		long startTime = System.currentTimeMillis();

		JobExecution jobExecution = jobLauncher.run(consumptionGoalJob, jobParameters);

		long endTime = System.currentTimeMillis();
		log.info("[BatchJob] 소비목표 배치 실행 종료 - 총 소요 시간: {}ms", (endTime - startTime));

		Integer resultCount = jdbcTemplate.queryForObject(
			"SELECT COUNT(*) FROM consumption_goal WHERE goal_at = DATE_FORMAT(CURDATE(), '%Y-%m-01')", Integer.class);

		assertThat(resultCount).isEqualTo(expectedCount);
		assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
	}
}