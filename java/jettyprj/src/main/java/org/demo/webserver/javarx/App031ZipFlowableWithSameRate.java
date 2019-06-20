package org.demo.webserver.javarx;

import io.reactivex.Flowable;
import org.apache.http.client.fluent.Request;
import org.demo.webserver.server.MyServer;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class App031ZipFlowableWithSameRate {
    private static int PORT = 8888;

    private static Logger logger = LoggerFactory.getLogger(App031ZipFlowableWithSameRate.class);

    public static void main(String[] args) throws Exception {
        Server s = MyServer.start(PORT);

        Flowable<Double> f1 = Flowable.interval(1000, TimeUnit.MILLISECONDS).
                map(v -> getPrice("/price")).
                map(v -> {
                    logger.info("{}", v);
                    return Double.parseDouble(v);
                }).throttleFirst(1000, TimeUnit.MILLISECONDS);


        Flowable<Double> f2 = Flowable.interval(1000, TimeUnit.MILLISECONDS).
                map(v -> getPrice("/price")).
                map(Double::parseDouble);

        f1.zipWith(f2, (v1, v2) ->
                String.format("%.2f %.2f %.2f", v1, v2, v1 + v2)).
                blockingSubscribe(str-> logger.info("{}"));

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