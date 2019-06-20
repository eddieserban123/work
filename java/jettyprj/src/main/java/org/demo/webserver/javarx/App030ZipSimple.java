package org.demo.webserver.javarx;

import io.reactivex.Flowable;
import org.apache.http.client.fluent.Request;
import org.demo.webserver.server.MyServer;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class App030ZipSimple {
    private static int PORT = 8888;

    private static Logger logger = LoggerFactory.getLogger(App030ZipSimple.class);

    public static void main(String[] args) throws Exception {


        Flowable<String> f1 = Flowable.just("Peter", "Paul", "Marry", "Bob");

        Flowable<Integer> f2 = Flowable.





        f1.zipWith(f2, (v1, v2) ->
                String.format("%.2f %.2f %.2f", v1, v2, v1 + v2)).
                blockingSubscribe(str-> logger.info("{}"));


    }

}