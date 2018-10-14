package com.purejpa.demo.jpapuredemo;

import com.purejpa.demo.jpapuredemo.entity.Course;
import com.purejpa.demo.jpapuredemo.repository.CourseRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NativeQueryTest {


    @Autowired
    EntityManager em;


    @Test
    public void native_query() {
        List<Course> res= em.createNativeQuery("select * from Course ", Course.class).getResultList();
        Assert.assertEquals(2,res.size());
    }

    @Test
    public void native_query_index() {
         Query res = em.createNativeQuery("select * from Course where id=?");
        res.setParameter(1, 10001L);
        Assert.assertEquals(1,res.getResultList().size());
    }

    @Test
    @Transactional
    public void native_query_param() {
        Query res = em.createNativeQuery("update Course set updated = sysdate()");
        int n = res.executeUpdate();
        Assert.assertEquals(2,n);
    }



}
