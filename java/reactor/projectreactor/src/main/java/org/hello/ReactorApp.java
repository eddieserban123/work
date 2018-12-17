package org.hello;

import reactor.core.publisher.Mono;

import java.io.IOException;

public class ReactorApp {

    public static void main(String[] args) throws InterruptedException, IOException {

        final Mono<String> mono = Mono.just("hello ");

        Thread t = new Thread(() -> mono
                .map(msg -> msg + "thread ")
                .subscribe(v ->
                        System.out.println(v + Thread.currentThread().getName())
                )
        );

        t.start();
        t.join();



    }

}
