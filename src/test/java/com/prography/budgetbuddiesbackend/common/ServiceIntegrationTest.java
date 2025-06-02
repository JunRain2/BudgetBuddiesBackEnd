package com.prography.budgetbuddiesbackend.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.TestExecutionListeners;

@IntegrationTest
@TestExecutionListeners(
	value = FlywayTestExecutionListener.class,
	mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceIntegrationTest {
}
