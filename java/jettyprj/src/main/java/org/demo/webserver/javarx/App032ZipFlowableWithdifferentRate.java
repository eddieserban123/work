package org.demo.webserver.javarx;

import io.reactivex.Flowable;
import org.apache.http.client.fluent.Request;
import org.demo.webserver.helpers.server.MyServer;
import org.eclipse.jetty.server.Server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class App032ZipFlowableWithdifferentRate {
    private static int PORT = 8888;

    public static void main(String[] args) throws Exception {
        Server s = MyServer.start(PORT);

        Flowable<Double> f1 = Flowable.interval(100, TimeUnit.MILLISECONDS).
                map(v -> getPrice("/price")).
                map(v -> {
                    System.out.println("  -> " + v);
                    return Double.parseDouble(v);
                });

        Flowable<Double> f2 = Flowable.interval(1000, TimeUnit.MILLISECONDS).
                map(v -> getPrice("/price")).
                map(Double::parseDouble);

        f1.zipWith(f2, (v1, v2) -> String.format("%.2f %.2f %.2f", v1, v2, v1 + v2)).blockingSubscribe(System.out::println);

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