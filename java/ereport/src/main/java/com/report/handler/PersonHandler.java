package com.report.handler;

import com.report.entity.Person;
import com.report.service.PersonService;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static javax.swing.text.html.FormSubmitEvent.MethodType.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class PersonHandler {

    private final PersonService profileService;

    PersonHandler(PersonService profileService) {
        this.profileService = profileService;
    }

    public Mono<ServerResponse> all(ServerRequest r) {
        return defaultReadResponse(profileService.all());
    }


    public Mono<ServerResponse> getById(ServerRequest r) {
        return defaultReadResponse(profileService.get(id(r)));
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
