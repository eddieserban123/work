package com.purejpa.demo.jpapuredemo.entity;

import javax.persistence.*;

@Entity
public class Passport {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String number;


    public Passport(String number) {
        this.number = number;
    }


    protected Passport() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Passport{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}


