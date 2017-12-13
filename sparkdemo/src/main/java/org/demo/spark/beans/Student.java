package org.demo.spark.beans;

import java.io.Serializable;

public class Student implements Serializable{

    Integer id;


    Integer classroom;
    String name;
    Integer mark1;
    Integer mark2;

    public Student() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassroom() {
        return classroom;
    }

    public void setClassroom(Integer classroom) {
        this.classroom = classroom;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMark1() {
        return mark1;
    }

    public void setMark1(Integer mark1) {
        this.mark1 = mark1;
    }

    public Integer getMark2() {
        return mark2;
    }

    public void setMark2(Integer mark2) {
        this.mark2 = mark2;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", classroom=" + classroom +
                ", name='" + name + '\'' +
                ", mark1=" + mark1 +
                ", mark2=" + mark2 +
                '}';
    }
}
