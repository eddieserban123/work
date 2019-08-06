package org.demo.webserver.reactor;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class App010FirstExample {

    private static Logger logger = LoggerFactory.getLogger(App010FirstExample.class);
    public static void main(String[] args) {
      //  createSimpleExamplewithSimpleSubscriber();
      //  createSimpleExamplewithBakpressureSubscriber01();
      //  doSmthAndThrowAnError();
       // textPlay1();
        textPlay3();

    }


    private static void createSimpleExamplewithSimpleSubscriber() {
        Flux<String> seq = Flux.just("foo", "bar", "foobar");
        //Arrays.asList("foo", "bar", "foobar");


        seq.subscribe(v-> logger.info(" data is {} " ,v),
                er-> logger.error("error {} ", er.getLocalizedMessage()),
                ()->  logger.info(" done !"));

    }


    private static void createSimpleExamplewithBakpressureSubscriber01() {
        Flux<String> seq = Flux.just("foo", "bar", "foobar");
        //Arrays.asList("foo", "bar", "foobar");

        seq.subscribe(v-> logger.info(" data is {} " ,v),
                er-> logger.error("error {} ", er.getLocalizedMessage()),
                ()->  logger.info(" done !"),
                (subscription -> subscription.request(10L)));

    }

    private static void textPlay1() {
        String words = "Amazingly few discotheques provide jukeboxe";

        Flux<String> alphabet = Flux.fromArray(words.split("")).distinct().sort().zipWith(Flux.range(1,35), (letter,count) ->
                String.format("%2d.  %s", count, letter));


        alphabet.subscribe(v-> logger.info("{}",v));
    }
    private static void textPlay2() {
        String words = "Amazingly few discotheques provide jukeboxe";

        Flux<String> alphabet = Flux.fromArray(words.split("")).
                distinct().concatWith(Mono.just("s")).
                sort().
                zipWith(Flux.range(1,35), (letter,count) ->
                String.format("%2d.  %s", count, letter));


        alphabet.subscribe(v-> logger.info("{}",v));
    }

    private static void textPlay3() {
        String words = "Amazingly few discotheques provide jukeboxes";

        Flux<String> alphabet = Flux.fromArray(words.split("")).
                groupBy( String::toString).
                flatMap(group -> Mono.zip(Mono.just(group.key()), group.count())).  //group.count().block expect onComplete!
                map(keyAndCount -> keyAndCount.getT1() + " => " + keyAndCount.getT2() + "; ");
        alphabet.subscribe(v-> logger.info("{}",v));
    }


    private static void createSimpleExamplewithBakpressureSubscriber02() {
        Flux<String> seq = Flux.just("foo", "bar", "foobar");
        //Arrays.asList("foo", "bar", "foobar");

        seq.subscribe(new Subscriber<>() {
            Subscription subscription;
            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                subscription.request(1L);
            }

            @Override
            public void onNext(String s) {
                logger.info("{}", s);
                subscription.request(1);
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

    private static void doSmthAndThrowAnError() {
        Flux<Integer> ints = Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("Got to 4");
                });
        ints.subscribe(i -> logger.info("{}", i),
                error -> logger.error("Error: {} ", error.getLocalizedMessage()));
    }



}
