package com.example.springwebflux.repository;


import com.example.springwebflux.entities.Officer;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface OfficerRepository extends ReactiveCrudRepository<Officer, String> {
        Flux<Officer> findByRank(@Param("rank") String rank);
        Flux<Officer> findByLast(@Param("last") String last);
}
