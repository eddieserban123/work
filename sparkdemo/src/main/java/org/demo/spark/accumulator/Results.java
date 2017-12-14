package org.demo.spark.accumulator;

import java.io.Serializable;

public class Results implements Serializable{

    private Double  val1;
    private Double  val2;

    private Integer n1;
    private Integer n2;


    public Results() {
        val1 = new Double(0);
        val2 = new Double(0);
        n1 = new Integer(0);
        n2 = new Integer(0);
    }

    public Results(Double val1,Double val2) {
        this.val1 = val1;
        this.val2 = val2;
        n1=n2=0;

    }

    public Integer getN1() {
        return n1;
    }

    public Integer getN2() {
        return n2;
    }

    public Double getVal1() {
        return val1;
    }

    public Double getVal2() {
        return val2;
    }

    public Results addVal1(Double val) {
        this.val1+=val;
        n1++;
        return this;
    }

    public Results addVal2(Double val) {
        this.val1+=val;
        n2++;
        return this;
    }
}
