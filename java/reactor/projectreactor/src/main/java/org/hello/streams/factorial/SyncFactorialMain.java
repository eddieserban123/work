package org.hello.streams.factorial;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

public class SyncFactorialMain {

    public static void main(String[] args) {
        Flux<Long> fibGen = Flux.generate(() -> Tuples.of(0L,1L),
                (state,sink) -> {
                    sink.next(state.getT1());
                    System.out.println("generated " + state.getT1());
                    return Tuples.of(state.getT2(), state.getT1() + state.getT2());
                } );

        fibGen.take(10).subscribe(v-> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cosumed " + v);
        });
    }
}
