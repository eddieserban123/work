package org.demo.webserver.javarx;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.apache.http.client.fluent.Request;
import org.demo.webserver.server.MyServer;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static org.demo.webserver.javarx.Color.*;

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
         //zip001Simple();
        //zip002Merge();
        //zip003Concat();

    }


    private static void zip001Simple() throws IOException {
        Flowable<String> f1 = createFlowable(1, TimeUnit.MILLISECONDS, "Peter", "Paul", "Marry", "Bob");
        Flowable<Color> f2 = createFlowable(1, TimeUnit.SECONDS, RED, BLUE, YELLOW);


        f1.zipWith(f2, (v1, v2) ->
                String.format("%s %s %s", v1, v2, v1 + " --> " + v2)).
                blockingSubscribe(str -> logger.info("{}", str));

        System.in.read();
    }


    private static void zip002Merge() throws IOException {


        Flowable<String> f1 = createFlowable(1, TimeUnit.MILLISECONDS, "Peter", "Paul", "Marry", "Bob");
        Flowable<Color> f2 = createFlowable(1, TimeUnit.MILLISECONDS, RED, BLUE, YELLOW);


        Flowable.merge(f1, f2).
                subscribe(str -> logger.info("{}", str));

        System.in.read();

    }

    private static void zip003Concat() throws IOException {


        Flowable<String> f1 = createFlowable(1, TimeUnit.SECONDS, "Peter", "Paul", "Marry", "Bob");
        Flowable<Color> f2 = createFlowable(1, TimeUnit.MILLISECONDS, RED, BLUE, YELLOW);


        Flowable.concat(f1, f2).
                subscribe(str -> logger.info("{}", str));

        System.in.read();

    }


    private static <T> Flowable<T> createFlowable(long interval, TimeUnit timeUnit, T... data) {

        return Flowable.<T>create(e -> {
            try {
                for (int i = 0; i < data.length; i++) {

                    Thread.sleep(timeUnit.toMillis(interval));
                    e.onNext(data[i]);

                }
            } catch (InterruptedException ex) {
                e.onError(ex);
            } finally {
                e.onComplete();
            }
        }, BackpressureStrategy.MISSING).subscribeOn(Schedulers.io());

    }
}