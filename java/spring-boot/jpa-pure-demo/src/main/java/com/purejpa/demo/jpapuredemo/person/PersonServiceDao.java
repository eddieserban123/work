package com.purejpa.demo.jpapuredemo.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Person> getAllPersons() {
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper(Person.class));
    }


    public Person getPersonById(int id) {
        return jdbcTemplate.queryForObject("select * from person where id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class));
    }


}
