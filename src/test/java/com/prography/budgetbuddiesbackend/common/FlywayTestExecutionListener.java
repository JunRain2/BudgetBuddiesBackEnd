package com.prography.budgetbuddiesbackend.common;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class FlywayTestExecutionListener extends AbstractTestExecutionListener {
	@Override
	public void afterTestMethod(TestContext testContext) throws Exception {
		DataSource ds = testContext.getApplicationContext().getBean(DataSource.class);

		Flyway flyway = Flyway.configure()
			.dataSource(ds)
			.cleanDisabled(false) // clean 허용
			.load();

		flyway.clean();
		flyway.migrate();
	}
}
