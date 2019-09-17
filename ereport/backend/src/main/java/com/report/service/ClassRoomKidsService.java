package com.report.service;

import com.report.entity.classroomkids.ClassRoomKids;
import com.report.entity.classroomkids.ClassRoomKidsKey;
import com.report.repository.ClassRoomKidsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@AllArgsConstructor
@Service
public class ClassRoomKidsService {

    private final ClassRoomKidsRepository classRoomKidsRepository;
    private final ApplicationEventPublisher publisher;

    /**
     * This shouldn/'t be call !
     * @return
     */
//    public Flux<ClassRoomKids> all() {
//        return classRoomKidsRepository.findAll();
//    }
//
//    public Mono<ClassRoomKids> get(ClassRoomKidsKey id) {
//        return classRoomKidsRepository.findById(id);
//    }

   // is still an uodate operation
//    public Mono<ClassRoomKids> delete(String id, String year_month) {
//        ClassRoomKidsKey key = new ClassRoomKidsKey(id, year_month);
//        return classRoomKidsRepository.findById(key).
//                flatMap(c -> classRoomKidsRepository.deleteById(c.getKey()).thenReturn(c));  //Then returns whatever Mono you put in it. thenReturn wraps whatever value you put into it into a Mono and returns it.
//    }

    //TODO implement a delete class for all years too ?

//    public Mono<ClassRoomKids> create(String classRoom_id, LocalDate date, List<String> person_ids) {
//        return classRoomKidsRepository.save(new ClassRoomKids().
//                setKey(new ClassRoomKidsKey(classRoom_id, date)).
//                ;
//    }


}
