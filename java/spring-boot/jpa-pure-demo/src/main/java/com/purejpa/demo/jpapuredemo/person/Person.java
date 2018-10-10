package com.purejpa.demo.jpapuredemo.person;

import java.time.LocalDateTime;

public class Person {
    Integer id;
    String name;
    String location;
    LocalDateTime birth_date;

    public Person() {

    }

    public Person(Integer id, String name, String location, LocalDateTime birth_date) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.birth_date = birth_date;
    }

    public Person(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", birth_date=" + birth_date +
                "}\n";
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDateTime birth_date) {
        this.birth_date = birth_date;
    }
}
