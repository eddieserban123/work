package com.purejpa.demo.jpapuredemo;

import com.purejpa.demo.jpapuredemo.entity.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Subgraph;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PerformanceRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(PerformanceRepositoryTest.class);


    @Autowired
    EntityManager em;



    @Test
    @Transactional
    public void createNPlusOneProblemTest(){
        List<Course> courses = em.createNamedQuery("find_all_courses",Course.class).getResultList();
        for(Course course:courses) {
            logger.info("Course {} , students {}",course.getName(), course.getStudents());
        }

    }

    @Test
    @Transactional
    public void solvingNPlusOneProblem_EntityGraph() {

        EntityGraph<Course> entityGraph = em.createEntityGraph(Course.class);
        Subgraph<Object> subGraph = entityGraph.addSubgraph("students");

        List<Course> courses = em
                .createNamedQuery("find_all_courses", Course.class)
                .setHint("javax.persistence.loadgraph", entityGraph)
                .getResultList();

        for(Course course:courses){
            logger.info("Course -> {} Students -> {}",course, course.getStudents());
        }
    }

    @Test
    @Transactional
    public void solvingNPlusOneProblem_JoinFetch() {
        List<Course> courses = em
                .createNamedQuery("query_get_all_courses_join_fetch", Course.class)
                .getResultList();
        for(Course course:courses){
            logger.info("Course -> {} Students -> {}",course, course.getStudents());
        }
    }
}
