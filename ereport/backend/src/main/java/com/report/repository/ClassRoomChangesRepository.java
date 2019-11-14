package com.report.repository;

import com.report.entity.classroom.ClassRoom;
import com.report.entity.classroom.ClassRoomKey;
import com.report.entity.classroomchanges.ClassRoomChanges;
import com.report.entity.classroomchanges.ClassRoomChangesKey;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ClassRoomChangesRepository extends ReactiveCassandraRepository<ClassRoomChanges, ClassRoomChangesKey> {

//    Mono<ClassRoomChanges> findByIdClassroomAndYear(ClassRoomChangesKey id);

    @Query(value = "SELECT * FROM  preschool.classroom_changes WHERE id_classroom = ?0 and year = ?1 ")
    Flux<ClassRoomChanges> findAllByKey(String id_classroom, int year);

}
