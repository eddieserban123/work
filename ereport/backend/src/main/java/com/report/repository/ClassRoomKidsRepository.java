package com.report.repository;

import com.datastax.driver.core.Session;
import com.report.entity.classroomkids.ClassRoomKids;
import com.report.entity.classroomkids.ClassRoomKidsKey;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.cql.support.PreparedStatementCache;
import org.springframework.data.cassandra.repository.query.CassandraEntityInformation;
import org.springframework.data.cassandra.repository.support.SimpleCassandraRepository;
import org.springframework.stereotype.Repository;

/*
This repository is responsbile for changes (new kids coming, or kids leaving a classroom).
Every time a kid is leaving/coming a classroom, the event is recorder in both tables: classroom_kids
and classroom_changes
 */
@Repository
public class ClassRoomKidsRepository extends SimpleCassandraRepository<ClassRoomKids, ClassRoomKidsKey> {

    private final Session session;
    private final CassandraOperations cassandraTemplate;
    private final PreparedStatementCache cache = PreparedStatementCache.create();

    public ClassRoomKidsRepository(
            Session session,
            CassandraEntityInformation entityInformation,
            CassandraOperations cassandraTemplate) {
        super(entityInformation, cassandraTemplate);
        this.session = session;
        this.cassandraTemplate = cassandraTemplate;
    }

    public void insertKidInClassRoom(String classRoomId, Integer year, Integer month, Integer day) {
        
    }



  //  Mono<ClassRoomKids> findById(ClassRoomKidsKey id);
}
