package com.report.initializer;

import com.report.entity.classroom.ClassRoom;
import com.report.entity.Person;
import com.report.entity.classroom.ClassRoomKey;
import com.report.repository.ClassRoomRepository;
import com.report.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
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
                        new ClassRoom("small 1", "2019-01").setCapacity(15).setRoomNumber("3").setDescription("a lovely room for new alpacas!"),
                        new ClassRoom("small 2", "2019-01").setCapacity(15).setRoomNumber("4").setDescription("a lovely room for teddy bear !"),
                        new ClassRoom("small 3", "2019-01").setCapacity(15).setRoomNumber("5").setDescription("a lovely room for baby goats !"),

                        new ClassRoom("medium 1", "2019-01").setCapacity(17).setRoomNumber("7").setDescription("let your dreams bloom!"),
                        new ClassRoom("medium 2", "2019-01").setCapacity(17).setRoomNumber("8").setDescription("who's enter here always smile"),
                        new ClassRoom("medium 3", "2019-01").setCapacity(17).setRoomNumber("9").setDescription("a little spark of kindness can put a colossal burst of sunshine"),


                        new ClassRoom("large 1", "2019-01").setCapacity(20).setRoomNumber("7").setDescription("think big start small!"),
                        new ClassRoom("large 2", "2019-01").setCapacity(20).setRoomNumber("8").setDescription("you are going to be great , keep going !"),
                        new ClassRoom("large 3", "2019-01").setCapacity(20).setRoomNumber("9").setDescription("start each day with a grateful heart")



                        ).flatMap(classRepository::save)).subscribe();
    }
}
