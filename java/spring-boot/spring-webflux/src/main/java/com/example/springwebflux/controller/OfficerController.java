package com.example.springwebflux.controller;

import com.example.springwebflux.entities.Officer;
import com.example.springwebflux.repository.OfficerRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("officer")
public class OfficerController {

    private OfficerRepository repository;

    public OfficerController(OfficerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    Flux<Officer> getAllOfficers() {
       return repository.findAll();
    }

    @GetMapping("{id}")
    Mono<Officer> getAllOfficers(@PathVariable("id") String id) {
        return repository.findById(id);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<Officer>> updateOfficer(@PathVariable(value = "id") String id,
                                                       @RequestBody Officer officer) {
        return repository.findById(id)
                .flatMap(existingOfficer -> {
                    existingOfficer.setRank(officer.getRank());
                    existingOfficer.setFirst(officer.getFirst());
                    existingOfficer.setLast(officer.getLast());
                    return repository.save(existingOfficer);
                })
                .map(updateOfficer -> new ResponseEntity<>(updateOfficer, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteOfficer(@PathVariable(value = "id") String id) {
        return repository.deleteById(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
