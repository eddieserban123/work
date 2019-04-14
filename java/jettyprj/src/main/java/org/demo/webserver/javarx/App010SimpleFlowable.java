package org.demo.webserver.javarx;

import io.reactivex.Flowable;
import org.apache.http.client.fluent.Request;
import org.demo.webserver.server.MyServer;
import org.eclipse.jetty.server.Server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class App010SimpleFlowable {
    private static int PORT = 8888;

    public static void main(String[] args) throws Exception {
        Server s =  MyServer.start(PORT);
        Flowable.interval(1000, TimeUnit.MILLISECONDS).
                map(v-> getPrice("/price")).
                map(Double::parseDouble).
                blockingSubscribe(System.out::println);
        s.join();
    }


    private static String getPrice(String url) throws IOException {
        return Request.Get("http://localhost:" + PORT + url)
                .execute()
                .returnContent()
                .asString()
                .trim();
    }
}