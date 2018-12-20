package com.example.springwebflux.handler;

import com.example.springwebflux.entities.Officer;
import com.example.springwebflux.repository.OfficerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class OfficerHandler {


    private OfficerRepository repository;

    public OfficerHandler(OfficerRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> listOfficer(ServerRequest request) {
        Flux<Officer> officers = repository.findAll();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).
                body(officers, Officer.class);
    }

    public Mono<ServerResponse> createOfficer(ServerRequest request) {
        Mono<Officer> officer = request.bodyToMono(Officer.class);
        return  officer.flatMap(o->
                ServerResponse.status(HttpStatus.CREATED).
                        contentType(MediaType.APPLICATION_JSON).
                        body(repository.save(o), Officer.class));
    }


    public Mono<ServerResponse> getOfficer(ServerRequest request){
        String id = request.pathVariable("id");

        Mono<Officer> officer = repository.findById(id);
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return officer.flatMap(o-> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).
                body(officer,Officer.class)).
                switchIfEmpty(notFound);


    }

}
