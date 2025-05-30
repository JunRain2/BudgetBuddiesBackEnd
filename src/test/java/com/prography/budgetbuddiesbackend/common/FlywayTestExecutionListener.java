package com.prography.budgetbuddiesbackend.common;

import org.flywaydb.core.Flyway;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public abstract class FlywayTestExecutionListener extends AbstractTestExecutionListener {
	@Override
	public void afterTestMethod(TestContext testContext) throws Exception {
		Flyway flyway = testContext.getApplicationContext().getBean(Flyway.class);
		flyway.clean();
		flyway.migrate();
	}
}
