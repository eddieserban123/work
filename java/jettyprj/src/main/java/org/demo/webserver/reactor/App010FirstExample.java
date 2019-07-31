package org.demo.webserver.reactor;

import reactor.core.publisher.Flux;

public class App010FirstExample {

    public static void main(String[] args) {
        Flux<String> seq1 = Flux.just("foo", "bar", "foobar");
    }
}
