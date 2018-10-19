package com.purejpa.demo.jpapuredemo.repository;

import com.purejpa.demo.jpapuredemo.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentRepositoryTest {


    private Logger logger = LoggerFactory.getLogger(StudentRepositoryTest.class);


    @Autowired
    StudentRepository rep;

    @Test
    public void findByIdTest() {
        Student st = rep.findById(20001L);
        logger.info("* student {}", st);
        logger.info("* passport {}", st.getPassport());
        }

}
