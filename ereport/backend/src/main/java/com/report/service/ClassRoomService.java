package com.report.service;

import com.report.entity.classroom.ClassRoom;
import com.report.entity.classroom.ClassRoomKey;
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

    public Mono<ClassRoom> get(ClassRoomKey id) {
        return classRoomRepository.findById(id);
    }

    public Mono<ClassRoom> update(String id, String year_month, Integer capacity, String roomNumber, String description) {
        ClassRoomKey key = new ClassRoomKey(id, year_month);
        return classRoomRepository.findById(key).
                map(c -> new ClassRoom().setKey(key).setCapacity(capacity).setDescription(description).setRoomNumber(roomNumber)).
                flatMap(classRoomRepository::save);   // Mono(Mono) !
    }

    public Mono<ClassRoom> delete(String id, String year_month) {
        ClassRoomKey key = new ClassRoomKey(id, year_month);
        return classRoomRepository.findById(key).
                flatMap(c -> classRoomRepository.deleteById(c.getKey()).thenReturn(c));  //Then returns whatever Mono you put in it. thenReturn wraps whatever value you put into it into a Mono and returns it.
    }

    //TODO implement a delete class for all years too ?

    public Mono<ClassRoom> create(String id, String year_month, Integer capacity, String roomNumber, String description) {
        return classRoomRepository.save(new ClassRoom().
                setKey(new ClassRoomKey(id, year_month)).
                setCapacity(capacity).
                setRoomNumber(roomNumber).
                setDescription(description));
    }


}
