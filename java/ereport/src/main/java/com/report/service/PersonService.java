package com.report.service;


import com.report.entity.Person;
import com.report.event.PersonCreatedEvent;
import com.report.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@AllArgsConstructor
@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final ApplicationEventPublisher publisher;

    public Flux<Person> all() {
        return personRepository.findAll();
    }

    public Mono<Person> get(String id) {
        return personRepository.findById(id);
    }

    public Mono<Person> update(String id, String name) {
        return personRepository.findById(id).
                map(p -> new Person(p.getId(), name)).
                flatMap(personRepository::save);   // Mono(Mono) !
    }

    public Mono<Person> delete(String id) {
        return personRepository.findById(id).
                flatMap(p -> personRepository.deleteById(p.getId()).thenReturn(p));  //Then returns whatever Mono you put in it. thenReturn wraps whatever value you put into it into a Mono and returns it.
    }

    public Mono<Person> create(String id, String name) {
        return personRepository.save(new Person(id, name)).
                doOnSuccess(entity -> publisher.publishEvent(new PersonCreatedEvent(entity)));
    }


}
