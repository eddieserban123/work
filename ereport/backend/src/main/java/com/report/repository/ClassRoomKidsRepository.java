package com.report.repository;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Session;
import com.report.entity.classroomkids.ClassRoomKids;
import com.report.entity.classroomkids.ClassRoomKidsKey;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.cql.support.CachedPreparedStatementCreator;
import org.springframework.data.cassandra.core.cql.support.PreparedStatementCache;
import org.springframework.data.cassandra.repository.query.CassandraEntityInformation;
import org.springframework.data.cassandra.repository.support.SimpleCassandraRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import static com.datastax.driver.core.querybuilder.QueryBuilder.*;

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

    public void insertKidInClassRoom(String classRoomId, LocalDate snapShotDate, String personId) {
        BatchStatement batch = new BatchStatement(BatchStatement.Type.LOGGED).add(insertKidInClassRoomQuery(classRoomId,snapShotDate,personId));

        session.execute(batch);
    }


    private BoundStatement insertKidInClassRoomQuery(String classRoomId, LocalDate snapShotDate, String personId) {
        return CachedPreparedStatementCreator.of(
                cache,

                insertInto("classroom_kids").
                        value("id_classroom", bindMarker("id_classroom")).
                        value("snapshot_date", bindMarker("snapshot_date")).
                        value("person_id", bindMarker("person_id"))
        )
                .createPreparedStatement(session)
                .bind()
                .setDate("snapshot_date", toCqlDate(snapShotDate))
                .setString("id_classroom", classRoomId)
                .setString("person_id", personId);


    }


    private com.datastax.driver.core.LocalDate toCqlDate(LocalDate date) {
        return com.datastax.driver.core.LocalDate.fromYearMonthDay(
                date.getYear(), date.getMonth().getValue(), date.getDayOfMonth());
    }

}
