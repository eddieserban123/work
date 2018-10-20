package com.purejpa.demo.jpapuredemo;


import com.purejpa.demo.jpapuredemo.repository.CourseRepository;
import com.purejpa.demo.jpapuredemo.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaApplication implements CommandLineRunner {


    private Logger logger = LoggerFactory.getLogger(JpaApplication.class);


    @Autowired
    CourseRepository rep;

    @Autowired
    StudentRepository stRep;


    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
      //  logger.info(" *** " + rep.findById(10001L));
        //rep.playWithEntityManager();
        //stRep.saveStudentWithPassport();
       // stRep.retrieveStudentandPassport();
       // rep.addReviewsForcourse();
       // rep.playWithEntityManager();
        stRep.setStudentAndCourses();
    }
}
