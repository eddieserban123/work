package com.report.event;

import com.report.entity.Person;
import org.springframework.context.ApplicationEvent;

/*
the event should extend ApplicationEvent
the publisher should inject an ApplicationEventPublisher object
the listener should implement the ApplicationListener interface
 */

public class PersonCreatedEvent extends ApplicationEvent {

    public PersonCreatedEvent(Person person) {
        super(person);
    }

}
