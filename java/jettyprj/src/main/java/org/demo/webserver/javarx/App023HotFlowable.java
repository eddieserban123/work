package org.demo.webserver.javarx;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/*
https://medium.com/@mohitsharma_16765/rxsubjects-cold-and-hot-observables-connectableobservable-6ab0f3020876
https://medium.com/@ik024/cold-and-hot-observables-rxjava2-b65b6c094cd9

*/
public class App023HotFlowable {
    private static Logger logger = LoggerFactory.getLogger(App023HotFlowable.class);


    public static void main(String[] args) throws Exception {

        System.in.read();

    }

}