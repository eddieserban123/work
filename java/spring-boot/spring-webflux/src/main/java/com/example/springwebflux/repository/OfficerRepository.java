package com.example.springwebflux.repository;


import com.example.springwebflux.entities.Officer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface OfficerRepository extends ReactiveCrudRepository<String, Officer> {
        Flux<Officer> findById(String id);
        Flux<Officer> findByName(String name);
}
