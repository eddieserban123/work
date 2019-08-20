package com.report.event.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.report.entity.Person;
import com.report.event.PersonCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

//https://github.com/oktadeveloper/okta-spring-webflux-react-example/tree/react-app/reactive-web/src/main/java/com/example/demo

@Component
public class PersonCreatedEventListener implements ApplicationListener<PersonCreatedEvent>, Consumer<FluxSink<PersonCreatedEvent>> {

    private ApplicationEventPublisher applicationEventPublisher;
    private final Executor executor;

    private final BlockingQueue<PersonCreatedEvent> queue = new LinkedBlockingQueue<>();

    public PersonCreatedEventListener(Executor executor, ApplicationEventPublisher applicationEventPublisher) {
        this.executor = executor;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public void accept(FluxSink<PersonCreatedEvent> sink) {
        executor.execute(() -> {
            while(true) {
                try {
                    PersonCreatedEvent event = queue.take();
                    sink.next(event);

                } catch(InterruptedException ex) {
                    ReflectionUtils.rethrowRuntimeException(ex);
                }
            }
        });
    }

    @Override
    public void onApplicationEvent(PersonCreatedEvent personCreatedEvent) {
        queue.offer(personCreatedEvent);
    }
//
//    @Bean
//    WebSocketHandler webSocketHandler(
//            ObjectMapper objectMapper, // <5>
//            PersonCreatedEventListener eventPublisher // <6>
//    ) {
//
//        Flux<PersonCreatedEvent> publish = Flux.create(eventPublisher).share(); // <7>
//
//        return session -> {
//
//            Flux<WebSocketMessage> messageFlux = publish.map(evt -> {
//                try {
//                    Person profile = (Person) evt.getSource(); // <1>
//                    Map<String, String> data = new HashMap<>(); // <2>
//                    data.put("id", profile.getId());
//                    return objectMapper.writeValueAsString(data); // <3>
//                } catch (JsonProcessingException e) {
//                    throw new RuntimeException(e);
//                }
//            }).map(str -> {
//                //log.info("sending " + str);
//                return session.textMessage(str);
//            });
//
//            return session.send(messageFlux);
//        };
//    }
}
