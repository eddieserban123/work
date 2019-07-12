package org.demo.webserver.javarx;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

/*
    Observable vs Flowable rxJava2. You may use Observable for example:

    handling GUI events working with short sequences (less than 1000 elements total)
    You may use Flowable for example:

    cold and non-timed sources generator like sources  network and database accessors
    Rxjava2 has a few backpressure strategies you can use depending on your usecase.
    by strategy i mean Rxjava2 supplies a way to handle the objects that cannot be processed because of the overflow (backpressure).

    here are the strategies (http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/BackpressureStrategy.html).
    for example if you want to not worry about the items that are overflowed you can use a drop strategy like this:

    observable.toFlowable(BackpressureStrategy.DROP)

    There should be a 128 item limit on the queue, after that there can be a overflow (backpressure).
    Even if its not 128 its close to that number. Hope this helps someone.

    If you need to change the buffer size from 128 it looks like it can be done like this (but watch any memory constraints:

    myObservable.toFlowable(BackpressureStrategy.MISSING).buffer(256); //but using MISSING might be slower.
    in software developement usually back pressure strategy means your telling the emitter to slow down a bit as the consumer cannot handle the velocity your emitting events.
 */
public class App000CreateFlowable {

    private static Logger logger = LoggerFactory.getLogger(App000CreateFlowable.class);


    public static void main(String[] args) {
        //example001();
        example002();
        //example003();
        //example004();
        //example005();
        //example006();
        //example007();


    }

    public static void example001() {
        Flowable.just(11.1, 12.3).
                map(v -> v * 2).
                blockingSubscribe(v-> logger.info("{} ", v));
    }

    /*
       launches creation and modification on their own threads !
     */
    public static void example002() {

        logger.info("main");
        Flowable.just(11.1, 12.3).
                map(v -> {
                    logger.info("mapper");
                    return v * 2;
                }).
                observeOn(Schedulers.computation()).  // for create
                subscribeOn(Schedulers.computation()).  //for map
                blockingSubscribe(System.out::println);
    }


    /*
   just wanted to play, it's not quite ok, if we want to simulate numbers as a pump, we should use a Publisher
    */
    public static void example003() {


        logger.info("main");
        Flowable.fromCallable(() -> Arrays.asList(1, 2, 3)).
                flatMap(e -> Flowable.fromIterable(e)).
                map(v -> v * 2).
                observeOn(Schedulers.computation()).  // for create
                subscribeOn(Schedulers.computation()).  //for map
                blockingSubscribe(System.out::println);
    }

    /*
        Creating my own stream
   */
    public static void example004() {


        Flowable<String> flow = Flowable.fromPublisher(p -> {
            p.onNext("aa ");
            p.onNext("bb ");
            p.onComplete();
        });

        flow.
                map(c -> c.concat(" b ")).
                blockingSubscribe(System.out::println);


    }


    /*
        Creating my own stream as java 9 with backpressure
    */
    public static void example005() {

        final PublishProcessor<String> processor = PublishProcessor.create();


        new Thread(() -> {
            logger.info("producer");
            for (int i = 0; i < 10; i++) {
                try {
                    processor.onNext("a ");
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            processor.onComplete();
        }).start();

        logger.info("main");

        Flowable.fromPublisher(processor).
                map(c -> c.concat(" b ")).
                subscribe(new Subscriber<>() {
                    Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        this.subscription = subscription;
                        subscription.request(1);
                    }

                    @Override
                    public void onNext(String s) {
                        logger.info( "{} received", s);
                        subscription.request(1);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {
                        logger.info(" On Complete !!!");
                    }
                });

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /*create an emitter capable of detecting ~onComplete but without backpressure, this is why you need a BackPressureStrategy*/
    public static void example006() {

        logger.info("main");

        Flowable.<String>create(e -> {
            int i = 0;
            while (!e.isCancelled()) {
                e.onNext(i++ + "number ");
            }
            logger.info("emitter");
            e.setCancellable(() -> System.out.println("we got cancel"));
        }, BackpressureStrategy.MISSING).
                subscribeOn(Schedulers.io()).
                map(c -> c.concat(" b ")).
                take(10).  // unsubscribe at 11th element
                subscribe(v ->
                        System.out.println(" value received " + v), err -> System.out.println(" error " + err), () -> System.out.println(" completed"));

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*multiple subscriptions*/
    public static void example007() {
        Flowable<Double> flowable = Flowable.just(11.1, 12.3).
                map(v -> v * 2);

        flowable.subscribe(v -> System.out.println(" first Subscriber " + v));
        flowable.subscribe(v -> System.out.println(" second Subscriber " + v));

    }


}