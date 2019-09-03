package com.report.initializer;

import com.report.entity.ClassRoom;
import com.report.entity.Person;
import com.report.repository.ClassRoomRepository;
import com.report.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Log4j2
@Component
//@Profile("demo")
public class DemoInitializer implements ApplicationListener<ApplicationReadyEvent> {


    private final PersonRepository personRepository;
    private final ClassRoomRepository classRepository;


    public DemoInitializer(PersonRepository repository, ClassRoomRepository classRepository) {
        this.personRepository = repository;
        this.classRepository = classRepository;
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        personRepository.deleteAll().thenMany(
                Flux.just(new Person("123", "John"),
                        new Person("125", "Mary"),
                        new Person("124", "Peter"),
                        new Person("126", "John")).flatMap(personRepository::save)).subscribe();


        classRepository.deleteAll().thenMany(
                Flux.just(
                        new ClassRoom("small 1", 15, "3"),
                        new ClassRoom("medium 1", 15, "7"),
                        new ClassRoom("large 1", 20, "10"),
                        new ClassRoom("small 2", 15, "4"),
                        new ClassRoom("medium 2", 15, "8"),
                        new ClassRoom("large 2", 20, "11")
                        ).flatMap(classRepository::save)).subscribe();
    }
}
