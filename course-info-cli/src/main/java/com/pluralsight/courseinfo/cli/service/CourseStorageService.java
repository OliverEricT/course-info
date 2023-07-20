package com.pluralsight.courseinfo.cli.service;

import java.util.List;
import java.util.Optional;

import com.pluralsight.courseinfo.domain.Course;
import com.pluralsight.courseinfo.repository.CourseRepository;

public class CourseStorageService {
	private static final String PS_BASE_URL = "https://app.pluralsight.com";

	private final CourseRepository courseRepository;

	public CourseStorageService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	public void storePluralSightCourses(List<PluralsightCourse> psCourses) {
		for (PluralsightCourse psCourse: psCourses) {
			Course course = new Course(
				psCourse.id(),
				psCourse.title(),
				psCourse.durationInMinutes(),
				PS_BASE_URL + psCourse.contentUrl(),
				Optional.empty()
			);
			courseRepository.saveCourse(course);
		}
	}
}
