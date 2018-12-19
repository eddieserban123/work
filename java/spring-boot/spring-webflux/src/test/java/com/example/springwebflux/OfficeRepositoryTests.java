package com.example.springwebflux;

import com.example.springwebflux.entities.Officer;
import com.example.springwebflux.entities.Rank;
import com.example.springwebflux.repository.OfficerRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoActionOperation;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OfficeRepositoryTests {

    @Autowired
    OfficerRepository repo;

    @Autowired
    ReactiveMongoOperations op;


    private List<Officer> officers = Arrays.asList(
            new Officer(Rank.CAPTAIN, "James", "Kirk"),
            new Officer(Rank.CAPTAIN, "Jean-Luc", "Picard"),
            new Officer(Rank.CAPTAIN, "Benjamin", "Sisko"),
            new Officer(Rank.CAPTAIN, "Kathryn", "Janeway"),
            new Officer(Rank.CAPTAIN, "Jonathan", "Archer"));


    @Before
    public void setUp() {
//        repo.deleteAll().thenMany(Flux.fromIterable(officers)).
//                flatMap(repo::save).
//                then().
//                block();

        op.collectionExists(Officer.class).
                flatMap(exists-> exists?op.dropCollection(Officer.class): Mono.just(exists)).
                flatMap(o->op.createCollection(Officer.class)).then().
                block();

        repo.saveAll(officers).then().block();
    }

    @Test
    public void search() {
        String name = "Picard";
        List<Officer> officers = repo.findByLast(name).collectList().block();

        Assert.assertEquals(name,Objects.requireNonNull(officers).get(0).getLast());
    }


}
