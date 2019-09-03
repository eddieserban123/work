package com.report.service;

import com.report.entity.ClassRoom;
import com.report.repository.ClassRoomRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@AllArgsConstructor
@Service
public class ClassRoomService {

    private final ClassRoomRepository classRoomRepository;
    private final ApplicationEventPublisher publisher;

    public Flux<ClassRoom> all() {
        return classRoomRepository.findAll();
    }

    public Mono<ClassRoom> get(String id) {
        return classRoomRepository.findById(id);
    }

    public Mono<ClassRoom> update(String name, Integer capacity, String roomNumber) {
        return classRoomRepository.findById(name).
                map(c -> new ClassRoom(c.getId(), capacity, roomNumber)).
                flatMap(classRoomRepository::save);   // Mono(Mono) !
    }

    public Mono<ClassRoom> delete(String id) {
        return classRoomRepository.findById(id).
                flatMap(c -> classRoomRepository.deleteById(c.getId()).thenReturn(c));  //Then returns whatever Mono you put in it. thenReturn wraps whatever value you put into it into a Mono and returns it.
    }

    public Mono<ClassRoom> create(String id, Integer capacity, String roomNo) {
        return classRoomRepository.save(new ClassRoom(id, capacity, roomNo));
    }


}
