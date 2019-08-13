package org.demo.webserver.javarx;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/*
https://medium.com/@mohitsharma_16765/rxsubjects-cold-and-hot-observables-connectableobservable-6ab0f3020876
https://medium.com/@ik024/cold-and-hot-observables-rxjava2-b65b6c094cd9

HOW TO CREATE HOT OBSERVABLES
We have two ways of creating HOT observables.
Subject : Using subjects, we can not only convert the cold into hot observable but also can create the hot
  observable from scratch.
ConnectableObservable : By using ConnectableObservables, we can only convert the cold observable into hot
  observable by using its publish and connect methods and various variants like refCount, autoConnect and replay etc.


*/
public class App023HotFlowable {
    private static Logger logger = LoggerFactory.getLogger(App023HotFlowable.class);


    public static void main(String[] args) throws Exception {

        //coldObservable();
        hotObservable();

        //asyncSubject();
        //behaviourSubject();
        //publishSubject();
     //   replySubject();

        System.in.read();

    }

    private static void coldObservable() {
        logger.info("---cold---");
        Observable<Long> cold = Observable.interval(200, TimeUnit.MILLISECONDS);


        cold.subscribe(v -> logger.info("subscriber1 recevied {}", v),
                err -> logger.error(" subscriber1 error {}", err));

        addSomeDelay();

        cold.subscribe(v -> logger.info("subscriber2 recevied {}", v),
                err -> logger.error(" subscriber2 error {}", err));


    }

    private static Observable<Integer> getColdObservable() {
        return Observable.create(subscriber -> {
            for (int i = 0; i < 2; i++) {
                logger.info("observable emit {} ", i);
                subscriber.onNext(i);
            }
            subscriber.onComplete();
        });
    }

    private static void hotObservable() {
        logger.info("---hot---");
        Observable<Long> cold = Observable.interval(200, TimeUnit.MILLISECONDS);

        ConnectableObservable<Long> hot =  cold.publish();


        hot.connect(); //this trigger  even if we don't have subscribers !

        hot.subscribe(v -> logger.info("subscriber1 recevied {}", v),
                err -> logger.error(" subscriber1 error {}", err));


        addSomeDelay();
        hot.subscribe(v -> logger.info("subscriber2 recevied {}", v),
                err -> logger.error(" subscriber2 error {}", err));


    }

    /*
    * AsyncSubject will emit only the last value to its subscribers when source observable completes.
    * AsyncSubject will get all the items emitted by source observable but only emit the last item when
    * source observable calls its onCompleted method.
    * */
    private static void asyncSubject() {

        Observable<Integer> cold = getColdObservable();

        AsyncSubject<Integer> asyncSubject = AsyncSubject.create();

        cold.subscribe(asyncSubject);

        asyncSubject.subscribe(v -> logger.info("subscriber1 recevied {}", v),
                err -> logger.error(" subscriber1 error {}", err));


        asyncSubject.subscribe(v -> logger.info("subscriber2 recevied {}", v),
                err -> logger.error(" subscriber2 error {}", err));


    }

    /*
    * BehaviourSubject emits the most recently item at the time of subscription or a default item if none
    * has been emitted and then continues the sequence until complete.
     */
    private static void behaviourSubject () {

        Observable<Long> cold = Observable.interval(1000, TimeUnit.MILLISECONDS);

        BehaviorSubject<Long> behaviorSubject = BehaviorSubject.
                createDefault(-1L);
        cold.subscribe(behaviorSubject);

        addSomeDelay();

        behaviorSubject.subscribe(v -> logger.info("subscriber1 recevied {}", v),
                err -> logger.error(" subscriber1 error {}", err));


        behaviorSubject.subscribe(v -> logger.info("subscriber2 recevied {}", v),
                err -> logger.error(" subscriber2 error {}", err));



    }

    /*
    *  PublishSubject is much similar to BehaviourSubject except that it emits only those items which are emitted
    * after the subscription. Also, It doesn’t give any default value.*/

    private static void publishSubject () {

        Observable<Long> cold = Observable.interval(1000, TimeUnit.MILLISECONDS);

        PublishSubject<Long> publishSubject = PublishSubject.
                create();
        cold.subscribe(publishSubject);

        addSomeDelay();

        publishSubject.subscribe(v -> logger.info("subscriber1 recevied {}", v),
                err -> logger.error(" subscriber1 error {}", err));


        publishSubject.subscribe(v -> logger.info("subscriber2 recevied {}", v),
                err -> logger.error(" subscriber2 error {}", err));



    }

    /*
     *  ReplySubject It emits all the emitted items to the subscribers regardless of when the subscribers
     * subscribes and then continues the sequence. There are also versions of ReplySubject that will throw
     * away the items if the buffer size gets filled with items or specified timespan gets passed.
     * */

    private static void replySubject () {

        Observable<Long> cold = Observable.interval(1000, TimeUnit.MILLISECONDS);

        ReplaySubject<Long> replySubject = ReplaySubject.
                create();
        cold.subscribe(replySubject);

        addSomeDelay();

        replySubject.subscribe(v -> logger.info("subscriber1 recevied {}", v),
                err -> logger.error(" subscriber1 error {}", err));


        replySubject.subscribe(v -> logger.info("subscriber2 recevied {}", v),
                err -> logger.error(" subscriber2 error {}", err));



    }

    private static void addSomeDelay() {

        try {
            logger.info("Wait for some seconds");
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}