package com.prography.budgetbuddiesbackend.common.converter;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * 앱의 YearMonth객체를 DB의 String 형식으로 yyyy-MM으로 저장하는 컨버터 클래스
 */
@Converter(autoApply = true)
public class YearMonthConverter implements AttributeConverter<YearMonth, String> {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

	@Override
	public String convertToDatabaseColumn(YearMonth attribute) {
		return attribute != null ? attribute.format(FORMATTER) : null;
	}

	@Override
	public YearMonth convertToEntityAttribute(String dbData) {
		return dbData != null ? YearMonth.parse(dbData, FORMATTER) : null;
	}
}
