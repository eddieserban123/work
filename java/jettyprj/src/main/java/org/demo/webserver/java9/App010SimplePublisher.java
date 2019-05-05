package org.demo.webserver.java9;

import org.apache.http.client.fluent.Request;
import org.demo.webserver.logging.Logger;
import org.demo.webserver.server.MyServer;
import org.eclipse.jetty.server.Server;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * Hello world!
 */
//   Flowable.just(4,6,7).subscribe(System.out::println);
public class App010SimplePublisher {
    private static int PORT = 8888;
    private static Logger logger = new Logger();

    public static void main(String[] args) throws Exception {
        Server s = MyServer.start(PORT);

        SubmissionPublisher<String> p = new SubmissionPublisher<>(
                Executors.newFixedThreadPool(4), 2);


        System.out.println(p.getMaxBufferCapacity());


        Flow.Subscriber<String> mySubscriber = new MySubscriber();
        p.subscribe(mySubscriber);

        // or if you don't need processing or other fancy stuff you could use
        //If a Subscriber has only one action that requests and processes all items,
        // we can consider using consume(Consumer) method (which returns a CompletableFuture object) like this:
//        p.consume((val) -> {
//            System.out.println(" val is " + val);
//        });
        for (int i = 0; i < 10; i++) {
            String val = getPrice("/price");
            p.submit(val);
            logger.log("Produced " + val);
        }

        s.join();
        logger.shutdown();
    }


    private static String getPrice(String url) throws IOException {
        return Request.Get("http://localhost:" + PORT + url)
                .execute()
                .returnContent()
                .asString()
                .trim();
    }
}

class MySubscriber implements Flow.Subscriber<String> {
    private static Logger logger = new Logger();
    Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);

    }

    @Override
    public void onNext(String s) {
        try {
            logger.log("consumed " + s);
            Thread.sleep(1000);
            subscription.request(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(Throwable throwable) {
    }

    @Override
    public void onComplete() {
        System.out.println("finished !!!");
    }
};


