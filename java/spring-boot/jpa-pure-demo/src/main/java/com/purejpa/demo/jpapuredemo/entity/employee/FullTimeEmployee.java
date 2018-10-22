package com.purejpa.demo.jpapuredemo.entity.employee;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class FullTimeEmployee extends Employee {

    public BigDecimal getSalary() {
        return salary;
    }


    public FullTimeEmployee(String name, BigDecimal salary) {
        super(name);
        this.salary = salary;
    }

    protected FullTimeEmployee() {
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    private BigDecimal salary;

    @Override
    public String toString() {
        return "FullTimeEmployee{" +
                "salary=" + salary +
                '}' + super.toString();
    }
}
