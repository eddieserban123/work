package org.demo.webserver.javarx;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.apache.http.client.fluent.Request;
import org.demo.webserver.server.MyServer;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class App020SimpleFlowable {
    private static int PORT = 8888;
    private static Logger logger = LoggerFactory.getLogger(App020SimpleFlowable.class);


    public static void main(String[] args) throws Exception {
        Server s =  MyServer.start(PORT);
        //mapExample();
        flatMapExample();
        s.join();
    }



    private static void mapExample() {
        Flowable.interval(1000, TimeUnit.MILLISECONDS).
                map(v-> getPrice("/price")).
                map(Double::parseDouble).
                blockingSubscribe(v-> logger.info("{}",v));
    }



    private static void flatMapExample() {
        Flowable.just("audi", "bmw", "opel").
                flatMap(car -> Flowable.just(getPrice("/price/" + car ))).
                subscribeOn(Schedulers.computation()).
                blockingSubscribe(val-> logger.info(val));
    }



    private static String getPrice(String url) throws IOException {
        return Request.Get("http://localhost:" + PORT + url)
                .execute()
                .returnContent()
                .asString()
                .trim();
    }
}