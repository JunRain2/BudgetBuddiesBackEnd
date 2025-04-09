package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ConsumptionGoalBatchAdapterTest {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job consumptionGoalJob;

	@Test
	void 소비목표_생성_배치가_정상적으로_완료된다() throws Exception {
		// given
		JobParameters jobParameters = new JobParametersBuilder()
			.addLong("timestamp", System.currentTimeMillis())
			.toJobParameters();

		// when
		JobExecution jobExecution = jobLauncher.run(consumptionGoalJob, jobParameters);

		// then
		assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
	}
}