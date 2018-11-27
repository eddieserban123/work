package com.purejpa.demo.jpapuredemo.repository;

import com.purejpa.demo.jpapuredemo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="courses")
public interface CourseSprinngDataRepository extends JpaRepository<Course,Long> {
}
