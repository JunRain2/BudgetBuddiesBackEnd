package com.prography.budgetbuddiesbackend.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

	public static void executeProcedure(DataSource dataSource, String classpathSqlPath) throws Exception {
		String sql = Files.readString(Path.of("src/test/resources/sql/" + classpathSqlPath), StandardCharsets.UTF_8);
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);

		jdbc.execute(sql);
	}
}
