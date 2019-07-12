package org.demo.webserver.java9.publisher;

import org.demo.webserver.helpers.logging.Logger;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.LongStream;

public class App030NumberProcessor {

    public static void main(String[] args) throws IOException {

        NumberPublisher publisher = new NumberPublisher(10L, 23L);
        NumberSubscriber subscriber = new NumberSubscriber(50);

        publisher.subscribe(subscriber);


    }

}

class NumberPublisher implements Flow.Publisher<Long> {
    ExecutorService executor;
    private Long start;
    private Long end;

    public NumberPublisher(Long start, Long end) {
        this.start = start;
        this.end = end;
        executor = Executors.newFixedThreadPool(2);
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Long> subscriber) {
        subscriber.onSubscribe(new NumberSubscription(executor, start, end, subscriber));
    }
}


class NumberSubscription implements Flow.Subscription {

    private Long start;
    private Long end;
    private Flow.Subscriber subscriber;
    private ExecutorService executor;
    private Future future;

    public NumberSubscription(ExecutorService executor, Long start, Long end, Flow.Subscriber subscriber) {
        this.executor = executor;
        this.start = start;
        this.end = end;
        this.subscriber = subscriber;
    }

    @Override
    public void request(long n) {
        System.out.println(" received a " + n);
        future = executor.submit(() -> LongStream.range(start, end).forEach(subscriber::onNext));
        try {
            future.get();
            subscriber.onComplete();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancel() {
        executor.shutdown();
        future.cancel(true);
    }
}

class NumberSubscriber implements Flow.Subscriber<Long> {


    private static Logger logger = new Logger();
    private Flow.Subscription subscription;
    private AtomicInteger count;

    public NumberSubscriber(int count) {
        this.count = new AtomicInteger(count);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        (this.subscription = subscription).request(1);
    }

    @Override
    public void onNext(Long item) {
        if (count.decrementAndGet() >= 0) {
            logger.log("** recv " + item);
        } else {
            subscription.cancel();
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {
        logger.log("** complete!");
    }
}