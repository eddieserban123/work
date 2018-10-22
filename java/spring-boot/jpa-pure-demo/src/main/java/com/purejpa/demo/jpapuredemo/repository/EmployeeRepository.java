package com.purejpa.demo.jpapuredemo.repository;

import com.purejpa.demo.jpapuredemo.entity.employee.Employee;
import com.purejpa.demo.jpapuredemo.entity.employee.FullTimeEmployee;
import com.purejpa.demo.jpapuredemo.entity.employee.PartTimeEmployee;
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
public class EmployeeRepository {

    @Autowired
    EntityManager em;


    private Logger logger = LoggerFactory.getLogger(EmployeeRepository.class);




    public Employee findById(Long id) {
        return em.find(Employee.class, id);
    }


    public void deleteById(Long id) {
        Employee c = em.find(Employee.class, id);
        em.remove(c);
    }

//    public List<Employee> findAll() {
//       TypedQuery<Employee> ret = em.createNamedQuery("findAllEmployee", Employee.class);
//        return ret.getResultList();
//    }

    public List<FullTimeEmployee> findAllFullEmployees() {
        TypedQuery<FullTimeEmployee> ret = em.createQuery("select f from FullTimeEmployee f", FullTimeEmployee.class);
        return ret.getResultList();
    }

    public List<PartTimeEmployee> findAllPartTimeEmployees() {
        TypedQuery<PartTimeEmployee> ret = em.createQuery("select p from PartTimeEmployee p", PartTimeEmployee.class);
        return ret.getResultList();
    }

    public Employee insert(Employee c) {
        if(c.getId() == null) {
            em.persist(c);
        } else
            em.merge(c);
        return c;
    }


}
