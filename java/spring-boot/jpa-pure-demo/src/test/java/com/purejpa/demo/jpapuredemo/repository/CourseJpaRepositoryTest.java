package com.purejpa.demo.jpapuredemo.repository;

import com.purejpa.demo.jpapuredemo.entity.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseJpaRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(CourseJpaRepositoryTest.class);


    @Autowired
    private CourseJpaRepository rep;

    @Test
    public void testPagination() {

        PageRequest page = PageRequest.of(0, 2);
        Page<Course> it = rep.findAll(page);
        logger.info(" *** " + it.getContent());
        Pageable pg;
        while(it.hasNext()) {
            pg = it.nextPageable();
            it = rep.findAll(pg);
            logger.info(" *** " + it.getContent());

        }
    }

}

