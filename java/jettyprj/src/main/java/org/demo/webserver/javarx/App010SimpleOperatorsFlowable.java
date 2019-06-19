package org.demo.webserver.javarx;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;

import static io.reactivex.Flowable.just;

public class App010SimpleOperatorsFlowable {

    private static Logger logger = LoggerFactory.getLogger(App010SimpleOperatorsFlowable.class);

    public static void main(String[] args) throws IOException {
        //example001TimeStamp();
        //example002Delay();
        //example003Cache();
        //example004Reduce();
        //example005Defer01();
        example005Defer02();
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
        Single<Integer> numbers = just(3, 5, -2, 9)
                .reduce(0, (totalSoFar, val) -> {
                    logger.info("totalSoFar={}, emitted={}", totalSoFar, val);
                    return totalSoFar + val;
                });

        numbers.subscribe(v-> logger.info("val1 {} ",v));

        System.in.read();
    }

    public static void example005Defer01() throws IOException {
        Flowable<String> flows = Flowable.just(getHttpResponse());
        logger.info("after blocking");
        flows.subscribe(val-> logger.info("{} received",val));
        System.in.read();
    }


    public static void example005Defer02() throws IOException {
        Flowable<String> flows = Flowable.defer(()->Flowable.just(getHttpResponse()));
        logger.info("after blocking");
        flows.subscribe(val-> logger.info("{} received",val));
        System.in.read();
    }




    private static String getHttpResponse() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "ana has got apples";

    }
}