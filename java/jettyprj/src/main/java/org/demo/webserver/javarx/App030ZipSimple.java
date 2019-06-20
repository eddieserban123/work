package org.demo.webserver.javarx;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import org.apache.http.client.fluent.Request;
import org.demo.webserver.server.MyServer;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

enum Color {
    RED,
    GREEN,
    BLUE,
    YELLOW
};

public class App030ZipSimple {
    private static int PORT = 8888;

    private static Logger logger = LoggerFactory.getLogger(App030ZipSimple.class);

    public static void main(String[] args) throws Exception {
       // zip001Simple();
        zip002Merge();

    }


    private static void zip001Simple() throws IOException {
        Flowable<String> f1 = Flowable.just("Peter", "Paul", "Marry", "Bob");

        Flowable<String> f2 = Flowable.create(e -> {
            for (int i = 0; i < 4; i++) {
                e.onNext(Color.values()[ThreadLocalRandom.current().nextInt(4)].name());
            }
            e.onComplete();
        }, BackpressureStrategy.MISSING);


        f1.zipWith(f2, (v1, v2) ->
                String.format("%s %s %s", v1, v2, v1 + " --> " + v2)).
                blockingSubscribe(str -> logger.info("{}", str));

        System.in.read();
    }

    private static void zip002Merge() throws IOException {
        Flowable<String> f1 = FlowFlowable.just("Peter", "Paul", "Marry", "Bob");

        Flowable<String> f2 = Flowable.create(e -> {
            for (int i = 0; i < 4; i++) {
                e.onNext(Color.values()[ThreadLocalRandom.current().nextInt(4)].name());
            }
            e.onComplete();
        }, BackpressureStrategy.MISSING);


        Flowable.merge(f1, f2).
                blockingSubscribe(str -> logger.info("{}", str));

        System.in.read();

    }
}