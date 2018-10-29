package com.purejpa.demo.jpapuredemo;


import com.purejpa.demo.jpapuredemo.repository.CourseRepository;
import com.purejpa.demo.jpapuredemo.repository.EmployeeRepository;
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

    @Autowired
    EmployeeRepository employeeRepository;
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
        //stRep.setStudentAndCourses();

//        employeeRepository.insert(new PartTimeEmployee("Jill", new BigDecimal("50")));
//        employeeRepository.insert(new FullTimeEmployee("Jack", new BigDecimal("10000")));
////
////        logger.info("All Employees -> {}",
////                employeeRepository.findAll());
//
//        logger.info("All FullTimeEmployees -> {}",
//                employeeRepository.findAllFullEmployees());
//        logger.info("All PartTimeEmployees -> {}",
//                employeeRepository.findAllPartTimeEmployees());

//        logger.info("All Courses With No Student -> {}", rep.allCoursesWithNoStudent());
//        logger.info("All Courses With At Least 2 Students -> {}", rep.allCoursesWithAtLeast2Students());
//        logger.info("All Courses order by Students -> {}", rep.allCoursesOrderByStudents());
//        logger.info("Courses like 100 steps -> {}", rep.coursesLike100Steps());
//
//        logger.info("Student with passport number like  -> {}", stRep.findStudentsWithPassportNumberFormat());

        rep.getAllCoursesInStream().forEach(c -> logger.info(c.toString()));


    }
}
