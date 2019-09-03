package com.report.repository;

import com.report.entity.ClassRoom;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClassRoomRepository extends ReactiveCassandraRepository<ClassRoom, String> {

    Mono<ClassRoom> findById(String id);
}
