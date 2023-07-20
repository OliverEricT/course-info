package com.courseinfo.cli.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.pluralsight.courseinfo.cli.service.CourseStorageService;
import com.pluralsight.courseinfo.cli.service.PluralsightCourse;
import com.pluralsight.courseinfo.domain.Course;
import com.pluralsight.courseinfo.repository.CourseRepository;

public class CourseStorageServiceTest {
	
	@Test
	void storePluralSightCourses() {
		CourseRepository repository = new InMemoryCourseRepository();
		CourseStorageService courseStorageService = new CourseStorageService(repository);

		PluralsightCourse ps1 = new PluralsightCourse(
			"1",
			"Title 1",
			"01:40:00.123",
			"/url-1",
			false
		);
		courseStorageService.storePluralSightCourses(List.of(ps1));

		Course expected = new Course(
			"1",
			"Title 1",
			100,
			"https://app.pluralsight.com/url-1",
			Optional.empty()
		);

		assertEquals(List.of(expected), repository.getAllCourses());
	}

	static class InMemoryCourseRepository implements CourseRepository {

		private final List<Course> courses = new ArrayList<Course>();

		@Override
		public void saveCourse(Course course) {
			courses.add(course);
		}

		@Override
		public List<Course> getAllCourses() {
			return courses;
		}

		@Override
		public void addNotes(String id, String notes){
			throw new UnsupportedOperationException();
		}

	}

}
