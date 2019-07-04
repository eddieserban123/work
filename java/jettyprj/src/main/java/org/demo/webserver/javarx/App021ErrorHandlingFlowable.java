package org.demo.webserver.javarx;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App021ErrorHandlingFlowable {
    private static Logger logger = LoggerFactory.getLogger(App021ErrorHandlingFlowable.class);


    public static void main(String[] args) throws Exception {
        throwException();
        //returnAvalueInsteadOfException();
        //signalErrorAndContinue();
        //onErrorResume();

        System.in.read();

    }



    private static void throwException() {
        Flowable<Integer> not56Stream = Flowable.just(3,4,8,22,56,7).
                map(v-> {
                    if(v == 56) {

                 

                            throw new RuntimeException(" Error, 56 found");
                    }
                    return v;
                }).
                map(v->v+1);


        not56Stream.subscribe(v-> logger.info("{}",v),
                error -> logger.error("{}", error.getLocalizedMessage())
                );

    }

    private static void returnAvalueInsteadOfException() {
        Flowable<Integer> not56Stream = Flowable.just(3,4,8,22,56,7).
                map(v-> {
                    if(v == 56) {
                        throw new RuntimeException(" Error, 56 found");
                    }
                    return v;
                }).
                map(v->v+1).
                onErrorReturn(e->0);


        not56Stream.subscribe(v-> logger.info("{}",v),
                error -> logger.error("{}", error.getLocalizedMessage())
        );

    }

    private static void signalErrorAndContinue() {
        Flowable<Integer> not56Stream = Flowable.just(3,4,8,22,56,7).
                flatMap(v-> doSomeAction(v).onErrorReturn(e->0)).
                map(v->v+1).
                onErrorReturn(e->0);


        not56Stream.subscribe(v-> logger.info("{}",v),
                error -> logger.error("{}", error.getLocalizedMessage())
        );

    }

    /*
    * returns a stream instead of an exception, useful for example to invoke a fallback
    * method that returns an alternate Stream
    * */
    private static void onErrorResume() {
        Flowable<Integer> not56Stream = Flowable.just(3,4,8,22,56,7,77).
                flatMap(v-> doSomeAction(v).onErrorResumeNext(e-> {
                    if(e instanceof IllegalArgumentException) {
                        return Flowable.error(new RuntimeException("fatal errors, invalid argument"));
                    }
                    return Flowable.just(32,43);
                })).
                map(v->v+1);


        not56Stream.subscribe(v-> logger.info("{}",v),
                error -> logger.error("{}", error.getLocalizedMessage())
        );

    }


    private static Flowable<Integer> doSomeAction(Integer v) {
        return Flowable.create(emitter->{
            if(v == 56) {
                throw new RuntimeException(" Error, 56 found");
            }
            if(v == 77) {
                throw new IllegalArgumentException(" Error illegal arg, 77 found");
            }
            emitter.onNext(v);

        }, BackpressureStrategy.LATEST);
    }


}