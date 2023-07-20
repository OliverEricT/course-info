package com.pluralsight.courseinfo.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CourseTest {
	
	@ParameterizedTest
	@CsvSource(textBlock = """
			,'name','url'
			'id',,'url'
			'id','name',
			""")
	void filled(String id, String name, String url) {
		assertThrows(IllegalArgumentException.class, () -> new Course(id, name, 0, url));
	}

}
