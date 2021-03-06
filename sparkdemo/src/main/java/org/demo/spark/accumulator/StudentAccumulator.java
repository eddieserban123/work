package org.demo.spark.accumulator;

import org.apache.spark.util.AccumulatorV2;
import org.demo.spark.beans.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StudentAccumulator extends AccumulatorV2<Student, Map<String, Results>> {

    final static Logger log = LoggerFactory.getLogger(StudentAccumulator.class);


    Map<String, Results> results = new ConcurrentHashMap<>();

    public StudentAccumulator() {

    }

    public StudentAccumulator(Map<String, Results> results) {
        this.results.putAll(results);
    }


    @Override
    public boolean isZero() {
        return results.isEmpty();
    }

    @Override
    public AccumulatorV2 copy() {
        log.error("****** copy students ");

        AccumulatorV2 newAcc = new StudentAccumulator();
        return newAcc;
    }

    @Override
    public void reset() {
        log.error("****** reset students ");
        this.results.clear();

    }

    @Override
    public void add(Student student) {
        try {
            log.info("****** add student " + student.getId() +
                    " on machine " + InetAddress.getLocalHost().getHostAddress() + " " +
                    "thread id "  + Thread.currentThread().getId());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String key = String.format("%d", student.getClassroom());
        results.putIfAbsent(key, new Results());

        results.computeIfPresent(key, (s, results) ->
                results.addVal1(
                                student.getMark1().doubleValue()).
                        addVal2(student.getMark2().doubleValue()));

    }

    @Override
    public Map<String, Results> value() {
        return this.results;
    }

    @Override
    public void merge(AccumulatorV2 accumulatorV2) {

        try {
            log.info("********* merge students -->  " + accumulatorV2.metadata().toString() +
                    " on machine " + InetAddress.getLocalHost().getHostAddress() + " " +
                    "thread id "  + Thread.currentThread().getId());

            ((Map<? extends String, ? extends Results>)accumulatorV2.value()).forEach((k,v)-> {
               log.info(String.format("#################################33 (%s %s)", k, v));
            });


        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.results.putAll((Map<? extends String, ? extends Results>) accumulatorV2.value());

    }


}