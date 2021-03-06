package com.purejpa.demo.jpapuredemo.repository;

import com.purejpa.demo.jpapuredemo.entity.Course;
import com.purejpa.demo.jpapuredemo.entity.Passport;
import com.purejpa.demo.jpapuredemo.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class StudentRepository {

    @Autowired
    EntityManager em;


    private Logger logger = LoggerFactory.getLogger(StudentRepository.class);




    public Student findById(Long id) {
        return em.find(Student.class, id);
    }


    public void deleteById(Long id) {
        Student c = em.find(Student.class, id);
        em.remove(c);
    }

    public List<Student> findAll() {
       TypedQuery<Student> ret = em.createNamedQuery("find_all_students", Student.class);
        return ret.getResultList();
    }

    public Student save(Student c) {
        if(c.getId() == null) {
            em.persist(c);
        } else
            em.merge(c);
        return c;
    }

        public void retrieveStudentandPassport() {
          Student student =   em.find(Student.class, 20001L);
          Passport passport = student.getPassport();
          logger.info("*** passport {}", passport);

        }

    public void saveStudentWithPassport() {
        Passport p = new Passport("Z123456");
        em.persist(p);

        Student student = new Student("titi");
        student.setPassport(p);
        em.persist(student);

    }

    @Transactional
    public void setStudentAndCourses() {
        Student student =   em.find(Student.class, 20001L);
        Course course1 = em.find(Course.class,10001L);
        Course course2 = em.find(Course.class,10002L);

        student.addCourse(course1);
        student.addCourse(course2);
        course1.addStudent(student);
        course2.addStudent(student);

        em.persist(student);

    }


    public List<Student> findStudentsWithPassportNumberFormat(){
        return em.createQuery("select s from Student s where s.passport.number like '%1234%'", Student.class).getResultList();

    }


}
