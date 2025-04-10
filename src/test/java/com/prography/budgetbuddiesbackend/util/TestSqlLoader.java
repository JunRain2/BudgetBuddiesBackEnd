package com.prography.budgetbuddiesbackend.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class TestSqlLoader {
	public static void executeSql(DataSource dataSource, String classpathSqlPath) throws IOException {
		String sql = Files.readString(Path.of("src/test/resources/sql/" + classpathSqlPath));
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);

		Arrays.stream(sql.split(";"))
			.map(String::trim)
			.filter(s -> !s.isBlank())
			.forEach(jdbc::execute);
	}
}
