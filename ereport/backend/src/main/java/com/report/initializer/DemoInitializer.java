package com.report.initializer;

import com.report.entity.Person;
import com.report.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Log4j2
@Component
@Profile("demo")
public class DemoInitializer  implements ApplicationListener<ApplicationReadyEvent> {


    private final PersonRepository repository;

    public DemoInitializer(PersonRepository repository) {
        this.repository = repository;
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        repository.deleteAll().thenMany(
                Flux.just(new Person( "123","John"),
                        new Person( "125","Mary"),
                        new Person( "124","Peter"),
                        new Person( "126","John")).flatMap(repository::save)).subscribe();
    }
}
