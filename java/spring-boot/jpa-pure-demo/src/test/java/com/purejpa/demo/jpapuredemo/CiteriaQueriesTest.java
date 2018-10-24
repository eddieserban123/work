package com.purejpa.demo.jpapuredemo;

import com.purejpa.demo.jpapuredemo.entity.Course;
import com.purejpa.demo.jpapuredemo.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CiteriaQueriesTest {

    @Autowired
    EntityManager em;

    private Logger logger = LoggerFactory.getLogger(CiteriaQueriesTest.class);



    @Test
    public void find_all_courses() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> cq= cb.createQuery(Course.class);

        Root<Course> root= cq.from(Course.class);
        TypedQuery<Course> query = em.createQuery(cq.select(root));
        logger.info("Courses {} ",query.getResultList());

    }


    @Test
    public void find_all_courses_have_100_steps() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> cq= cb.createQuery(Course.class);

        Root<Course> root= cq.from(Course.class);

        Predicate like =  cb.like(root.get("name"),"%100%");
        TypedQuery<Course> query = em.createQuery(cq.select(root).where(like));
        logger.info(" ***** Courses like 100 steps  {} ",query.getResultList());

    }

    @Test
    public void find_all_courses_with_students() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> cqc= cb.createQuery(Course.class);

        Root<Course> rootCourse= cqc.from(Course.class);
        Predicate isEmpty = cb.isNotEmpty(rootCourse.get("students"));
        TypedQuery<Course> query = em.createQuery(cqc.select(rootCourse).where(isEmpty));
        logger.info(" *****All courses with students  {} ",query.getResultList());

    }
}
