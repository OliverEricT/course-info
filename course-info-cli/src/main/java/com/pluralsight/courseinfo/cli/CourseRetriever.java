package com.pluralsight.courseinfo.cli;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pluralsight.courseinfo.cli.service.CourseRetrievalService;
import com.pluralsight.courseinfo.cli.service.CourseStorageService;
import com.pluralsight.courseinfo.cli.service.PluralsightCourse;
import com.pluralsight.courseinfo.repository.CourseRepository;

import static java.util.function.Predicate.not;

public class CourseRetriever {
	
	private static final Logger LOG = LoggerFactory.getLogger(CourseRetriever.class);

	public static void main(String... args){
		LOG.info("CourseRetriever started!");
		if (args.length == 0){
			LOG.warn("Please provide an author name as first argument.");
			return;
		}

		try{
			retrieveCourses(args[0]);
		} catch(Exception ex) {
			LOG.error("Unexpected error");
			ex.printStackTrace();
		}
		
	}

	private static void retrieveCourses(String authorId) {
		LOG.info("Retrieving courses for author '{}'",authorId);
		CourseRetrievalService courseRetrievalService = new CourseRetrievalService();
		CourseRepository courseRepository = CourseRepository.openCourseRepository("./courses.db");
		CourseStorageService courseStorageService = new CourseStorageService(courseRepository);

		List<PluralsightCourse> coursesToStore = courseRetrievalService.getCoursesFor(authorId)
			.stream()
			.filter(not(PluralsightCourse::isRetired))
			.toList();
		LOG.info("Retrieved the following {} courses {}", coursesToStore.size(), coursesToStore);
		courseStorageService.storePluralSightCourses(coursesToStore);
		LOG.info("Courses successfully stored");
	}
}
