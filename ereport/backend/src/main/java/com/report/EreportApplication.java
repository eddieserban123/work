package com.report;

import com.report.entity.Person;
import com.report.entity.classroomchanges.ClassRoomChanges;
import com.report.entity.classroomchanges.ClassRoomChangesKey;
import com.report.repository.ClassRoomChangesRepository;
import com.report.repository.ClassRoomRepository;
import com.report.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;


//export SPRING_PROFILES_ACTIVE=demo
//mvn spring-boot:run

@EnableCassandraRepositories
@SpringBootApplication
public class EreportApplication implements CommandLineRunner {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private ClassRoomChangesRepository classRoomChanges;


    @Autowired
    private AbstractApplicationContext context;

    public static void main(String[] args) {

        SpringApplication.run(EreportApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        Flux<ClassRoomChanges> cc = classRoomChanges.findAllById(Arrays.asList(new ClassRoomChangesKey("3", 2019)));

        cc.subscribe(System.out::println);
    }

    // not needed anymore since we have an initializer
    private void createPersons() {
//        final Person p1 = new Person("123", "John");
//        final Person p2 = new Person("125", "Mary");
//        final Person p3 = new Person("124", "Peter");
//        final Person p4 = new Person("126", "John");
//
//        personRepository.insert(List.of(p1, p2, p3, p4)).subscribe();
//        System.out.println("starting findAll");
//        personRepository.findAll().log().map(Person::getName).subscribe(p -> System.out.println("findAll: " + p));
//        System.out.println("starting findByKeyFirstName");
//
    }
}
