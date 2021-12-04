package com.lisapuetz.degreeroadmapbuilder.models.data;

import com.lisapuetz.degreeroadmapbuilder.models.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Integer> {
}
