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
public class App020SimplePublisherWithProcessor {
    private static int PORT = 8888;
    private static Logger logger = new Logger();

    public static void main(String[] args) throws Exception {
        Server s = MyServer.start(PORT);

        SubmissionPublisher<String> producer = new SubmissionPublisher<>(
                Executors.newFixedThreadPool(4), 5);


        System.out.println(producer.getMaxBufferCapacity());


        Flow.Subscriber<Double> mySubscriber = new MySubscriber02();
        Flow.Processor processor = new MyProcessor();
        producer.subscribe(processor);
        processor.subscribe(mySubscriber);
      //  processor.subscribe(mySubscriber);

        for (int i = 0; i < 10; i++) {
            String val = getPrice("/price");
            producer.submit(val);
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


class MySubscriber02 implements Flow.Subscriber<Double> {
    private static Logger logger = new Logger();
    Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);

    }

    @Override
    public void onNext(Double s) {
        try {
            logger.log("consumed " + s);
            Thread.sleep(1000);
            subscription.request(1);
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



class MyProcessor implements Flow.Processor<String, Double> {

    private static Logger logger = new Logger();
    private Flow.Subscriber subscriber;
    private Flow.Subscription subscription;


    @Override
    public void subscribe(Flow.Subscriber subscriber) {
        this.subscriber = subscriber;

    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscriber.onSubscribe(subscription);
    }

    @Override
    public void onNext(String item) {
        Double val = Double.valueOf(item);
        logger.log("transformed " + val);
        subscriber.onNext(val);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}

