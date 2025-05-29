package com.prography.budgetbuddiesbackend;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;

@TestConfiguration
public class TestContainerConfig {

	@Bean
	@ServiceConnection
	public MySQLContainer<?> createMySQLContainer() {
		MySQLContainer<?> container =new MySQLContainer<>("mysql:8.0");
		container.start();

		return container;
	}
}
