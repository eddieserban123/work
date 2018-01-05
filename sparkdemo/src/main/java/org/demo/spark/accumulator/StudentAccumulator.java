package org.demo.spark.accumulator;

import org.apache.spark.util.AccumulatorV2;
import org.demo.spark.beans.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        log.error("****** add student " + student.getId());
        String key = String.format("%d,%d", student.getClassroom(),student.getId());
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
        log.error("****** merge studentss ");
        this.results.putAll((Map<? extends String, ? extends Results>) accumulatorV2.value());

    }


}