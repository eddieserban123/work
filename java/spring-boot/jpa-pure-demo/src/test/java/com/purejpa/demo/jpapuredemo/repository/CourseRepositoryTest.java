package com.purejpa.demo.jpapuredemo.repository;

import com.purejpa.demo.jpapuredemo.entity.Course;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseRepositoryTest {


    @Autowired
    CourseRepository rep;

    @Test
    public void findByIdTest() {
        Course c = rep.findById(10001L);
        Assert.assertEquals("Spring boot", c.getName());
    }

    @Test
    @DirtiesContext
    public void deleteByIdTest() {
        rep.deleteById(10002L);
        Course c = rep.findById(10002L);
        Assert.assertNull(c);
    }

    @Test
    public void findAllTest() {
        Assert.assertEquals(2,rep.findAll().size());
    }

    @Test
    @DirtiesContext
    public void saveTest() {
        Course c = rep.save(new Course("math"));
        Assert.assertEquals(c,rep.findById(c.getId()));
        Assert.assertEquals(3,rep.findAll().size());
    }

}
