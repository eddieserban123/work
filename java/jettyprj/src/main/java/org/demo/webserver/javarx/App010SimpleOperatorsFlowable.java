package org.demo.webserver.javarx;

import io.reactivex.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class App010SimpleOperatorsFlowable {

    private static Logger logger = LoggerFactory.getLogger(App010SimpleOperatorsFlowable.class);

    public static void main(String[] args) throws IOException {
        exampleDelay001();
    }

    public static void exampleDelay001() throws IOException {
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
}