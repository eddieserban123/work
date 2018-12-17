package org.hello.streams;

import java.io.IOException;
import java.util.concurrent.Flow;
import java.util.stream.Stream;

public class AppMain {

    public static void main(String[] args) throws IOException {


        new StreamPublisher<>(()-> Stream.of(1,2,3,4,5,6)).subscribe(new Flow.Subscriber<>() {

            Flow.Subscription sub;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
             //   this.sub = subscription;
             //   sub.request(1);
            }

            @Override
            public void onNext(Integer item) {
                System.out.println(item);
               // sub.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("** finish");
            }
        });

        System.in.read();
    }
}
