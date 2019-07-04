package org.demo.webserver.javarx;

import io.reactivex.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;

/*
onErrorResumeNext( ) — instructs an Observable to emit a sequence of items if it encounters an error
onErrorReturn( ) — instructs an Observable to emit a particular item when it encounters an error
onExceptionResumeNext( ) — instructs an Observable to continue emitting items after it encounters an exception (but not another variety of throwable)
retry( ) — if a source Observable emits an error, resubscribe to it in the hopes that it will complete without error
retryWhen( ) — if a source Observable emits an error, pass that error to another Observable to determine whether to resubscribe to the source

* */
public class App022TimeoutFlowable {
    private static Logger logger = LoggerFactory.getLogger(App022TimeoutFlowable.class);


    public static void main(String[] args) throws Exception {

        timeoutExample();

        System.in.read();

    }


    private static void timeoutExample() {
        Flowable<String> executionTimes = Flowable.just("doTask1", "doTask2", "doTask5").
                flatMap(App022TimeoutFlowable::doSomeAction).
                timeout(5000, TimeUnit.MILLISECONDS).
                //onErrorReturn(er-> nameTask("doJiraBackLog", 3000));
                        onExceptionResumeNext(Flowable.just(nameTask("doJiraBackLog", 3000)));


        executionTimes.blockingSubscribe(v -> logger.info("{}", v));

    }


    private static Flowable<String> doSomeAction(String taskName) {
        int simulateWorkTime;
        switch (taskName.toLowerCase()) {
            case "dotask1":
                simulateWorkTime = 2000;
                break;
            case "dotask2":
                simulateWorkTime = 6000;
                break;
            case "dotask3":
                simulateWorkTime = 3000;
                break;
            default:
                simulateWorkTime = 3000;
        }
        try {
            Thread.sleep(simulateWorkTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Flowable.just(nameTask(taskName, simulateWorkTime));
    }

    private static String nameTask(String taskName, int simulateWorkTime) {
        return String.format("task %s took %s", taskName, simulateWorkTime);
    }
}
