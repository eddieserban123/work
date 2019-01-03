package com.interview.reactive;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class AppMaim {
    /**
     * Hello world!
     *
     */
    public static class App
    {
        public static void main( String[] args ) throws InterruptedException {
            System.out.println( "Hello World!" );
            SubmissionPublisher<Integer> pub = new SubmissionPublisher<>();
            PlusTenProcessor processor = new PlusTenProcessor();
            PrintSubscriber prtSub = new PrintSubscriber();

            pub.subscribe(processor);
            processor.subscribe(prtSub);



            for(int i=0;i<10;i++) {
                pub.submit(i);
            }



            Thread.sleep(2000);
        }
    }

    public static class PlusTenProcessor extends SubmissionPublisher<Integer> implements Flow.Subscriber<Integer> {

        Flow.Subscription sub;
        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.sub = subscription;
            sub.request(1);
        }

        @Override
        public void onNext(Integer item) {
            submit(item + 10);
            sub.request(1);
        }

        @Override
        public void onError(Throwable throwable) {
            closeExceptionally(throwable);
        }

        @Override
        public void onComplete() {
            close();
        }
    }

    public static class PrintSubscriber implements Flow.Subscriber<Integer> {

        Flow.Subscription  subscription;
        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            subscription.request(1);
        }

        @Override
        public void onNext(Integer item) {
            System.out.println(item);
            subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable) {
            System.out.println(throwable.getLocalizedMessage());
        }

        @Override
        public void onComplete() {
            System.out.println("Done !");
        }
    }
}
