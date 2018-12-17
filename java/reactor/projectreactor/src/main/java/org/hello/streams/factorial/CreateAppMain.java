package org.hello.streams.factorial;

import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicBoolean;

public class CreateAppMain {

    public static void main(String[] args) {

        Flux<Long> fibGen = Flux.create(e-> {
            long current = 1, prev = 0;
            AtomicBoolean stop = new AtomicBoolean(false);
            e.onDispose(() -> {
                        stop.set(true);
                        System.out.println("****Stop");
                    }
            );
            while (current>0){
                System.out.println("generated " + current);
                e.next(current);
                long next =  current + prev;
                prev = current;
                current = next;
            }
            e.complete();
        });

        fibGen.take(50).subscribe(s->
                {
                    try {
                        Thread.sleep(10);
                        System.out.println("consumed " + s);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

    }
}
