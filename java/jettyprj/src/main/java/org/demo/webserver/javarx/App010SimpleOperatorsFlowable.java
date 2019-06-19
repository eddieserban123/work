package org.demo.webserver.javarx;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class App010SimpleOperatorsFlowable {

    private static Logger logger = LoggerFactory.getLogger(App010SimpleOperatorsFlowable.class);

    public static void main(String[] args) throws IOException {
        //example001TimeStamp();
        //example002Delay();
        //example003Cache();
        //example004Reduce();
        example005Collect();
    }


    public static void example001TimeStamp() throws IOException {
        Flowable.range(0, 10).
                doOnNext(v->logger.info(" value {} ",v)).
                timestamp().
                subscribe(v -> logger.info("recv {}" , v),
                        (er) -> logger.error("error {}", er),
                        () -> {
                            System.out.println("Completed!");
                        });

        System.in.read();
    }


    public static void example002Delay() throws IOException {
        Flowable.range(0, 10).
                doOnNext(v->logger.info(" value {} ",v)).
                delay(5000, TimeUnit.MILLISECONDS).
                subscribe(v -> logger.info("recv {}" , v),
                        (er) -> logger.error("error {}", er),
                        () -> {
                            System.out.println("Completed!");
                        });

        System.in.read();
    }

    public static void example003Cache() throws IOException {
        Flowable<Integer>  flow = Flowable.<Integer>create(e-> {
            Thread.sleep(5000); //simulate long run
            e.onNext(10);
        }, BackpressureStrategy.MISSING)
                .cache();

        flow.subscribe(v-> logger.info("val1 {} ",v));
        flow.subscribe(v-> logger.info("val2 {} ",v));

        System.in.read();
    }

    public static void example004Reduce() throws IOException {
        Single<Integer> numbers = Flowable.just(3, 5, -2, 9)
                .reduce(0, (totalSoFar, val) -> {
                    logger.info("totalSoFar={}, emitted={}", totalSoFar, val);
                    return totalSoFar + val;
                });

        numbers.subscribe(v-> logger.info("val1 {} ",v));
        System.in.read();
    }

    public static void example005Collect() throws IOException {
      Single<List<Integer>> list =  Flowable.just(3, 5, -2, 9).
                collect(ArrayList::new, (container, value) -> {
                    logger.info("adding to container {} value {}", container, value);
                    container.add(value);
                });

        list.subscribe(v-> logger.info("val {} ",v));

        System.in.read();

    }
}