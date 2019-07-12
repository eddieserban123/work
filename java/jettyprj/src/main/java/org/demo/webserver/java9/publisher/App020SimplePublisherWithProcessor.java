package org.demo.webserver.java9.publisher;

import org.apache.http.client.fluent.Request;
import org.demo.webserver.helpers.logging.Logger;
import org.demo.webserver.helpers.server.MyServer;
import org.eclipse.jetty.server.Server;

import java.io.IOException;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Function;

/**
 * Hello world!
 */
public class App020SimplePublisherWithProcessor {
    private static int PORT = 8888;
    private static Logger logger = new Logger();

    public static void main(String[] args) throws Exception {
        Server s = MyServer.start(PORT);

        SubmissionPublisher<String> producer = new SubmissionPublisher<>();


        System.out.println(producer.getMaxBufferCapacity());


        Flow.Subscriber<Double> mySubscriber = new MySubscriber02();
        Flow.Processor processor = new MyProcessor(Double::parseDouble);
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


class MyProcessor extends SubmissionPublisher<Double> implements Flow.Processor<String, Double>{

    private Flow.Subscription subscription;
    private Function<String, Double>  transformer;
    private static Logger logger = new Logger();

    public MyProcessor(Function<String, Double> transformer) {
        this.transformer = transformer;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(String item) {

        Double val = transformer.apply(item);
        logger.log("transformed " + item);
        getSubscribers().forEach(s-> submit(val));
        subscription.request(1);

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

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
            Thread.sleep(10000);
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


