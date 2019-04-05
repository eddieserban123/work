package org.demo.webserver;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import org.apache.http.client.fluent.Request;
import org.demo.webserver.server.MyServer;
import org.eclipse.jetty.server.Server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
//   Flowable.just(4,6,7).subscribe(System.out::println);
public class App {
    private static int PORT = 8888;

    public static void main(String[] args) throws Exception {
        Server s = MyServer.start(PORT);
        Observable

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


