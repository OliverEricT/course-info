package com.pluralsight.courseinfo.server;

import java.util.Comparator;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pluralsight.courseinfo.domain.Course;
import com.pluralsight.courseinfo.repository.CourseRepository;
import com.pluralsight.courseinfo.repository.RepositoryException;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/courses")
public class CourseResource {
	private static final Logger LOG = LoggerFactory.getLogger(CourseResource.class);

	private final CourseRepository courseRepository;

	public CourseResource(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Stream<Course> getCourses() {
		try {
			return courseRepository
				.getAllCourses()
				.stream()
				.sorted(Comparator.comparing(Course::id));
		} catch (RepositoryException e) {
			LOG.error("Could not retrieve courses from the database", e);
			throw new NotFoundException();
		}
	}

	@POST
	@Path("/{id}/notes")
	@Consumes(MediaType.TEXT_PLAIN)
	public void addNotes(@PathParam("id") String id, String notes) {
		courseRepository.addNotes(id, notes);
	}

}
