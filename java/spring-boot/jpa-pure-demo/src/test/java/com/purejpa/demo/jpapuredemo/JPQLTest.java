package com.purejpa.demo.jpapuredemo;

import com.purejpa.demo.jpapuredemo.repository.StudentRepository;
import com.purejpa.demo.jpapuredemo.repository.StudentRepositoryTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JPQLTest {

    @Autowired
    EntityManager em;

    private Logger logger = LoggerFactory.getLogger(JPQLTest.class);



    @Test
    public void join_student_course() {
        List<Object[]> res = em.createQuery("select s,c from Student s join s.courses c ").getResultList();
        for (Object[] it : res) {
            logger.info("*** student {}  course {}", it[0], it[1]);
        }
    }
}
