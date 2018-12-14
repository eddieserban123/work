package org.hello;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        System.out.println("Hello World!");
        Flux<String> seq1 = Flux.just("foo", "bar", "foobar");

        List<String> iterable = Arrays.asList("foo", "bar", "foobar");
        Flux<String> seq2 = Flux.fromIterable(iterable);


        Mono<String> noData = Mono.empty();
        Mono<String> data = Mono.just("foo");
        Flux<Integer> numbersFromFiveToSeven = Flux.range(5, 300);

        numbersFromFiveToSeven.subscribe(new Subscriber<Integer>() {

            Subscription  sub;

            List<Integer> ls = new ArrayList<>();
            @Override
            public void onSubscribe(Subscription subscription) {
                this.sub = subscription;
                sub.request(10);
            }

            @Override
            public void onNext(Integer integer) {

                ls.add(integer);
                if(ls.size()>=10) {
                    System.out.println(ls);
                    ls.clear();
                    sub.request(10);
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });


        Flux<String> flux = Flux.generate(
                () -> 0,
                (Integer state, SynchronousSink<String> sink) -> {
                    sink.next("3 x " + state + " = " + 3*state);
                    if (state == 10) sink.complete();
                    return state + 1;
                });

        flux.collectList().subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(1);
            }

            @Override
            public void onNext(List<String> strings) {
                strings.forEach(System.out::println);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {
                System.out.println("Done !");
            }
        });
    }


}
