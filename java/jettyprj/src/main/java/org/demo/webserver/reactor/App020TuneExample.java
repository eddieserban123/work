package org.demo.webserver.reactor;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

//https://stackoverflow.com/questions/52244808/backpressure-mechanism-in-spring-web-flux

public class App020TuneExample {

    private static Logger logger = LoggerFactory.getLogger(App020TuneExample.class);
    public static void main(String[] args) {
       limitRateExample();


    }


    private static void limitRateExample() {
        Flux<String> seq = Flux.range(1,1000).
                limitRate(10).map(String::valueOf);  // or publishOn(...,prefetchSize)



        seq.subscribe(new Subscriber<>() {
            LogSubscription subscription;
            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = new LogSubscription(subscription);
                subscription.request(100L);
            }

            @Override
            public void onNext(String s) {
                logger.info("{}", s);
                subscription.request(100);
            }

            @Override
            public void onError(Throwable throwable) {
                logger.error("{} " + throwable.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                logger.info(" completed! ");
            }
        });

    }


}


class LogSubscription  implements Subscription {

    Subscription subscription;
    private static Logger logger = LoggerFactory.getLogger(LogSubscription.class);

    LogSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void request(long l) {
        logger.info(" requested {} ",l);
        subscription.request(l);
    }

    @Override
    public void cancel() {

    }
}