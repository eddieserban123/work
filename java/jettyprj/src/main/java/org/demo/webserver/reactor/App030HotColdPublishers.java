package org.demo.webserver.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;

public class App030HotColdPublishers {
    private static Logger logger = LoggerFactory.getLogger(App020TuneExample.class);


    public static void main(String[] args) throws InterruptedException, IOException {

        hotPublisher();

        System.in.read();
    }


    private static void hotPublisher() throws InterruptedException {
        ConnectableFlux<Object> publish = Flux.create(fluxSink -> {
            while (true) {
                fluxSink.next(System.currentTimeMillis());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).publish();

        Flux<Object>  hot = publish.autoConnect();

        hot.subscribeOn(Schedulers.parallel()).subscribe(v-> logger.info("s1 -> {} ", v));
        Thread.sleep(1000);
        hot.subscribeOn(Schedulers.parallel()).subscribe(v-> logger.info("s2 -> {} ", v));


    }

}
