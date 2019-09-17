package com.report.repository;

import com.report.entity.classroom.ClassRoom;
import com.report.entity.classroom.ClassRoomKey;
import com.report.entity.classroomkids.ClassRoomKids;
import com.report.entity.classroomkids.ClassRoomKidsKey;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.cassandra.repository.query.CassandraEntityInformation;
import org.springframework.data.cassandra.repository.support.SimpleCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class ClassRoomKidsRepository extends SimpleCassandraRepository<ClassRoomKids, ClassRoomKidsKey> {
    /**
     * Create a new {@link SimpleCassandraRepository} for the given {@link CassandraEntityInformation} and
     * {@link CassandraTemplate}.
     *
     * @param metadata   must not be {@literal null}.
     * @param operations must not be {@literal null}.
     */
    public ClassRoomKidsRepository(CassandraEntityInformation<ClassRoomKids, ClassRoomKidsKey> metadata, CassandraOperations operations) {
        super(metadata, operations);
    }

  //  Mono<ClassRoomKids> findById(ClassRoomKidsKey id);
}
