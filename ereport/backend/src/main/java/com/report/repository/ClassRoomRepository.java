package com.report.repository;

import com.report.entity.classroom.ClassRoom;
import com.report.entity.classroom.ClassRoomKey;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClassRoomRepository extends ReactiveCassandraRepository<ClassRoom, ClassRoomKey> {

    Mono<ClassRoom> findById(ClassRoomKey id);
}
