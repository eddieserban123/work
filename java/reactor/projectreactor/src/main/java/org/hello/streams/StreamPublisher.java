package org.hello.streams;


import org.reactivestreams.Subscriber;

import java.util.Iterator;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class StreamPublisher<T> implements Flow.Publisher<T> {
    private final Supplier<Stream<T>> streamSupplier;

    public StreamPublisher(Supplier<Stream<T>> streamSupplier) {
        this.streamSupplier = streamSupplier;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        StreamSubscription subscription = new StreamSubscription(subscriber);
        subscriber.onSubscribe(subscription);
        subscription.doOnSubscribed();
    }

    private class StreamSubscription implements Flow.Subscription {
        Flow.Subscriber<? super T> subscriber;
        Iterator<? extends T> iterator;
        private final AtomicBoolean isTerminated = new AtomicBoolean(false);
        private final AtomicReference<Throwable> error = new AtomicReference<>();


        public StreamSubscription(Flow.Subscriber<? super T> subscriber) {
            this.subscriber = subscriber;
            try {
                iterator = streamSupplier.get().iterator();
            } catch (Throwable e) {
                error.set(e);
            }

            this.iterator = iterator;

        }

        @Override
        public void request(long n) {
            if (n <= 0 && !terminate()) {
                subscriber.onError(new IllegalArgumentException("negative subscription request"));
                return;
            }

            for (int i = 0; i < n && iterator.hasNext(); i++) {
                try {
                    subscriber.onNext(iterator.next());
                } catch (Throwable e) {
                    if (!terminate()) {
                        subscriber.onError(e);
                    }
                }
            }

            if (!iterator.hasNext() && !terminate()) {
                subscriber.onComplete();
            }

        }

        @Override
        public void cancel() {
            terminate();
        }

        private boolean terminate() {
            return isTerminated.getAndSet(true);
        }

        public void doOnSubscribed() {
            Throwable throwable = error.get();
            if (throwable != null && !terminate()) {
                subscriber.onError(throwable);
            }
        }

        private boolean isTerminated() {
            return isTerminated.get();
        }
    }

    public static void main(String[] args) {
        double s = 3456;
        double orig = s;



        for (int i = 0;i<365;i++) {
            s = s + 3.5*s/1000;
        }

        System.out.println(100*s/orig);
    }
}
