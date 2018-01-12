package org.demo.spark.accumulator;

import org.apache.spark.util.AccumulatorV2;
import org.demo.spark.beans.Student;
import org.demo.spark.beans.TradeTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TradeTimeAccumulator extends AccumulatorV2<TradeTime, Map<String, Results>> {

    final static Logger log = LoggerFactory.getLogger(TradeTimeAccumulator.class);


    Map<String, Results> results = new ConcurrentHashMap<>();

    public TradeTimeAccumulator() {

    }

    public TradeTimeAccumulator(Map<String, Results> results) {
        this.results.putAll(results);
    }


    @Override
    public boolean isZero() {
        return results.isEmpty();
    }

    @Override
    public AccumulatorV2 copy() {
        log.error("****** copy TRADETIME");

        AccumulatorV2 newAcc = new TradeTimeAccumulator();
        return newAcc;
    }

    @Override
    public void reset() {
        log.error("****** reset TRADETIME ");
        this.results.clear();

    }

    @Override
    public void add(TradeTime trade) {
        try {
            log.info("****** add tradeEvent " + trade.getMarket_event() +
                    " on machine " + InetAddress.getLocalHost().getHostAddress() + " " +
                    "thread id "  + Thread.currentThread().getId());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String key = String.format("%s", trade.getMarket_event());
        results.putIfAbsent(key, new Results());

        results.computeIfPresent(key, (s, results) ->
                results.addVal1(
                                trade.getOffset().doubleValue()).
                        addVal2(trade.getTradetime_millis().doubleValue()));

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