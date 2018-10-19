package com.purejpa.demo.jpapuredemo.repository;

import com.purejpa.demo.jpapuredemo.entity.Course;
import com.purejpa.demo.jpapuredemo.entity.Review;
import com.purejpa.demo.jpapuredemo.entity.ReviewRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CourseRepository {

    @Autowired
    EntityManager em;


    public Course findById(Long id) {
        return em.find(Course.class, id);
    }

    public void deleteById(Long id) {
        Course c = em.find(Course.class, id);
        em.remove(c);
    }

    public List<Course> findAll() {
       TypedQuery<Course> ret = em.createNamedQuery("find_all_courses", Course.class);
        return ret.getResultList();
    }

    public Course save(Course c) {
        if(c.getId() == null) {
            em.persist(c);
        } else
            em.merge(c);
        return c;
    }

    public void playWithEntityManager() {
        Course course1 = new Course("course1");
        em.persist(course1);

        Course course2 = findById(10001L);
        //em.detach(course2); //or em.clear()
        course2.setName("JPA in 50 Steps - Updated");



    }
    @Transactional
    public void addReviewsForcourse() {
        Course course = findById(10003L);

        Review review1 = new Review(ReviewRating.FIVE, "Briliant!!");
        Review review2 = new Review(ReviewRating.FIVE, "Hatsoff");

        course.addReview(review1);
        review1.setCourse(course);
        course.addReview(review2);
        review2.setCourse(course);

        em.merge(review1);
        em.merge(review2);





    }
}
