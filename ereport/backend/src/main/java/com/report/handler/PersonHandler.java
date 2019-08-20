package com.report.handler;

import com.report.entity.Person;
import com.report.service.PersonService;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
@AllArgsConstructor
public class PersonHandler {

    private final PersonService personService;

    private final CassandraTemplate cassandraTemplate;


    public Mono<ServerResponse> all(ServerRequest r) {
        return defaultReadResponse(personService.all());
    }


    public Mono<ServerResponse> getById(ServerRequest r) {
        return defaultReadResponse(personService.get(id(r)));
    }

    public Mono<ServerResponse> create(ServerRequest r) {
        Mono<Person> mono = r.bodyToMono(Person.class)
                .flatMap(p -> personService.create(p.getId(), p.getName()));

        return Mono.from(mono).flatMap(p ->
                created(URI.create("/person/" + p.getId())).
                        contentType(MediaType.APPLICATION_JSON).build()
        );
    }

    public Mono<ServerResponse> deleteById(ServerRequest r) {
        return personService.delete(id(r)).flatMap(
               p->  noContent().build()
        );
    }

    public Mono<ServerResponse> updateById(ServerRequest r) {
        Mono<Person> mono = r.bodyToMono(Person.class).
                flatMap(p -> personService
                        .update(id(r), p.getName()));
        //should treat the error case also
        return Mono.from(mono).flatMap(p-> noContent().build()
        );
    }


    private static Mono<ServerResponse> defaultReadResponse(Publisher<Person> profiles) {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(profiles, Person.class);
    }

    private static String id(ServerRequest r) {
        return r.pathVariable("id");
    }
}
