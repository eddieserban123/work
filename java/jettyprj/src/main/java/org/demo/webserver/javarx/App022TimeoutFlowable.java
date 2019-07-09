package org.demo.webserver.javarx;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Flow;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/*
onErrorResumeNext( ) — instructs an Observable to emit a sequence of items if it encounters an error
onErrorReturn( ) — instructs an Observable to emit a particular item when it encounters an error
onExceptionResumeNext( ) — instructs an Observable to continue emitting items after it encounters an exception
(but not another variety of throwable)
retry( ) — if a source Observable emits an error, resubscribe to it in the hopes that it will complete without error
retryWhen( ) — if a source Observable emits an error, pass that error to another Observable to determine whether to resubscribe to the source

http://androidahead.com/2017/12/05/rxjava-operators-part-6-timeout-operator/

*/
public class App022TimeoutFlowable {
    private static Logger logger = LoggerFactory.getLogger(App022TimeoutFlowable.class);


    public static void main(String[] args) throws Exception {

       // startTimeoutTest();
        timeoutExample();
        System.in.read();

    }

    private static void startTimeoutTest() {
        Flowable
                .timer(0, TimeUnit.SECONDS)
                .flatMap((i) -> emitItems(20))

                .timeout(500, TimeUnit.MILLISECONDS)
                .onErrorResumeNext(Flowable.just(-1))
                .subscribe(v -> logger.info("{}", v),
                        err -> logger.error("{}", err),
                        () -> logger.info("completed !!! "));
    }

    private static void timeoutExample() {
        Flowable<String> executionTimes = Flowable.<String>create(e -> {
                  String taskNames[]= {"doTask1", "doTask2", "doTask3"};
                  e.onNext(doSomeAction(taskNames[0]));
                    e.onNext(doSomeAction(taskNames[1]));
                    e.onNext(doSomeAction(taskNames[2]));
                    e.onComplete();

                }, BackpressureStrategy.BUFFER).
                observeOn(Schedulers.computation()).  // for create
                subscribeOn(Schedulers.computation()).  //for map
                timeout(3000, TimeUnit.MILLISECONDS).
                onErrorResumeNext(Flowable.just("doJiraBackLog"));



        executionTimes.subscribe(v -> logger.info("{}", v),
                err -> logger.error("{}", err),
        () -> logger.info("completed !!! "));

    }



    private static String doSomeAction(String taskName) {
        int simulateWorkTime;
        switch (taskName.toLowerCase()) {
            case "dotask1":
                simulateWorkTime = 2000;
                break;
            case "dotask2":
                simulateWorkTime = 5000;
                break;
            case "dotask3":
                simulateWorkTime = 3000;
                break;
            default:
                simulateWorkTime = 3000;
        }
        try {
            Thread.sleep(simulateWorkTime);
        //   logger.info("taskname {} executed", taskName);
        } catch (InterruptedException e) {
            logger.error("taskname {} is takint too much time", taskName);
        }

        return nameTask(taskName, simulateWorkTime);
    }

    private static String nameTask(String taskName, int simulateWorkTime) {
        return String.format("task %s took %s", taskName, simulateWorkTime);
    }


    private static Flowable<Integer> emitItems(Integer numberOfItems) {
        logger.info("emitItems() - numberOfItems: " + numberOfItems);

        return Flowable

                // Emit N items based on the "numberOfItems" parameter
                .range(0, numberOfItems)

                .doOnNext((number) -> {
                    try {
                        int timeout = ThreadLocalRandom.current().nextInt(500) +100;

                        logger.info( "Item: " + number + " will be emitted with a delay around: " + timeout + "ms");
                        Thread.sleep(timeout);
                        logger.info( "emitItems() - Emitting number: " + number);

                    } catch (InterruptedException e) {
                        logger.info( "Got an InterruptedException!");
                    }

                });
    }

}
