package com.example.springwebflux;

import com.example.springwebflux.entities.Officer;
import com.example.springwebflux.entities.Rank;
import com.example.springwebflux.repository.OfficerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class OfficerInit implements CommandLineRunner {

    private ReactiveMongoOperations  op;
    private OfficerRepository repo;

    public OfficerInit(ReactiveMongoOperations op, OfficerRepository repo) {
        this.op = op;
        this.repo = repo;
    }

    private List<Officer> officers = Arrays.asList(
            new Officer(Rank.CAPTAIN, "James", "Kirk"),
            new Officer(Rank.CAPTAIN, "Jean-Luc", "Picard"),
            new Officer(Rank.CAPTAIN, "Benjamin", "Sisko"),
            new Officer(Rank.CAPTAIN, "Kathryn", "Janeway"),
            new Officer(Rank.CAPTAIN, "Jonathan", "Archer"));



    @Override
    public void run(String... args) throws Exception {


        op.collectionExists(Officer.class).
                flatMap(exists-> exists?op.dropCollection(Officer.class): Mono.just(exists)).
                flatMap(o->op.createCollection(Officer.class)).then().
                block();

        repo.saveAll(officers).then().block();
    }
}
