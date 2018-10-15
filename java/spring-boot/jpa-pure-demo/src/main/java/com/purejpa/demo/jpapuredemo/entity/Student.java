package com.purejpa.demo.jpapuredemo.entity;

import javax.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;



    @OneToOne
    private Passport passport;


    public Student(String name) {
        this.name = name;
    }


    protected Student() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", passport=" + passport +
                '}';
    }
}


