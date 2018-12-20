package com.example.springwebflux;

import com.example.springwebflux.entities.Officer;
import com.example.springwebflux.entities.Rank;
import com.example.springwebflux.repository.OfficerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReactiveOfficersApplicationTests {

    @Autowired
    private OfficerRepository repository;

    @Autowired
    private WebTestClient webClient;


    private List<Officer> officers = Arrays.asList(
            new Officer(Rank.CAPTAIN, "James", "Kirk"),
            new Officer(Rank.CAPTAIN, "Jean-Luc", "Picard"),
            new Officer(Rank.CAPTAIN, "Benjamin", "Sisko"),
            new Officer(Rank.CAPTAIN, "Kathryn", "Janeway"),
            new Officer(Rank.CAPTAIN, "Jonathan", "Archer"));

    @Before
    public void setUp() {
        repository.deleteAll()
                .thenMany(Flux.fromIterable(officers))
                .flatMap(repository::save)
                .doOnNext(System.out::println)
                .then()
                .block();
    }

    @Test
    public void testCreateOfficer() {
        Officer officer = new Officer(Rank.LIEUTENANT, "Eddie", "Serban");


        webClient.post().uri("/officer").
                contentType(MediaType.APPLICATION_JSON_UTF8).
                body(Mono.just(officer), Officer.class).
                exchange().
                expectStatus().isCreated().
                expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8).
                expectBody().jsonPath("$.id").isNotEmpty().
                jsonPath("$.first").isEqualTo("Eddie").
                jsonPath("$.last").isEqualTo("Serban");

    }

    @Test
    public void testGetAllOfficers() {
        webClient.get().uri("/officer/all").
                exchange().
                expectStatus().isOk().
                expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8).
                expectBodyList(Officer.class);
    }

    @Test
    public void testGetOfficer() {
        webClient.get().uri("/officer/{id}", officers.get(0).getId()).
                exchange().
                expectStatus().isOk().
                expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8).
                expectBody(Officer.class).
                consumeWith(System.out::println);
    }


}
