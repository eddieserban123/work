package org.demo.webserver.javarx;

import io.reactivex.Flowable;
import org.apache.http.client.fluent.Request;
import org.demo.webserver.helpers.server.MyServer;
import org.eclipse.jetty.server.Server;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

/*
https://github.com/ReactiveX/RxJava/wiki/Backpressure
*/
public class App050FlowableWithSubscriber {
    private static int PORT = 8888;

    public static void main(String[] args) throws Exception {
        Server s = MyServer.start(PORT);

        Flowable<Double> f1 = Flowable.interval(100, TimeUnit.MILLISECONDS).
                map(v -> getPrice("/price")).
                map(Double::parseDouble);

        f1.subscribe(new MySubscriber());
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


class MySubscriber implements Subscriber<Double> {
    private final long INTERVAL = 1000;
    Subscription subscription;
    long oldTime;

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        oldTime = System.currentTimeMillis();
        subscription.request(1000);
    }

    @Override
    public void onNext(Double aDouble) {
        try {
            System.out.println(LocalTime.now() + " --> " + aDouble);
            long diff = System.currentTimeMillis() - oldTime;
            while (diff < INTERVAL) {
                Thread.sleep(INTERVAL - diff);
                diff = System.currentTimeMillis() - oldTime;
            }
            subscription.request(1);
            oldTime = System.currentTimeMillis();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {
    }
}
