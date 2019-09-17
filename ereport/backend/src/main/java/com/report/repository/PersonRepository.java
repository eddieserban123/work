package com.report.repository;

import com.report.entity.Person;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface  PersonRepository extends ReactiveCassandraRepository<Person, String> {

    Mono<Person> findById(String id);
}
