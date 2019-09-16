package com.report.controller;


import com.report.entity.Person;
import com.report.service.PersonService;
import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;


//@RestController
//@RequestMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
//@Profile("classic")
public class PersonController {

    private final MediaType mediaType = MediaType.APPLICATION_JSON;
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    Publisher<Person> getAll() {
        return personService.all();
    }

    @GetMapping("/{id}")
    Publisher<Person> getById(@PathVariable("id") String id) {
        return personService.get(id);
    }

    @PostMapping
    Publisher<ResponseEntity<Person>> create(@RequestBody Person person) {
        return personService.create(person.getId(), person.getName(), person.getBirth()).
                map(p -> ResponseEntity.created(URI.create("/person/" + p.getId())).
                        contentType(mediaType).build());
    }

    @DeleteMapping("/{id}")
    Publisher<Person> delete(@PathVariable("id") String id) {
        return personService.delete(id);
    }

    @PutMapping("/{id}")
    Publisher<ResponseEntity<Person>> updateById(@PathVariable("id") String id, @RequestBody Person person) {
        return Mono.just(person).
                flatMap(p -> personService.update(id, p.getName(), p.getBirth())).
                map(p -> ResponseEntity.ok().contentType(mediaType).build());
    }


}
