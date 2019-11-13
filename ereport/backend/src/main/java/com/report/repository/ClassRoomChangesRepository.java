package com.report.repository;

import com.report.entity.classroom.ClassRoom;
import com.report.entity.classroom.ClassRoomKey;
import com.report.entity.classroomchanges.ClassRoomChanges;
import com.report.entity.classroomchanges.ClassRoomChangesKey;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClassRoomChangesRepository extends ReactiveCassandraRepository<ClassRoomChanges, ClassRoomChangesKey> {

    Mono<ClassRoomChanges> findByIdClassroomAndYear(ClassRoomChangesKey id);

//    @Query("select * from preschool.classroom_changes solr_query = ?0")
//    Mono<ClassRoomChanges> findMono(String query);

}