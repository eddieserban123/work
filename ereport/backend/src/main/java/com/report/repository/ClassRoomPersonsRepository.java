package com.report.repository;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Session;
import com.report.entity.classroompersons.ClassRoomPersons;
import com.report.entity.classroompersons.ClassRoomPersonsKey;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.data.cassandra.core.cql.support.CachedPreparedStatementCreator;
import org.springframework.data.cassandra.core.cql.support.PreparedStatementCache;
import org.springframework.data.cassandra.repository.query.CassandraEntityInformation;
import org.springframework.data.cassandra.repository.support.SimpleReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import static com.datastax.driver.core.querybuilder.QueryBuilder.*;

/*
This repository is responsbile for changes (new kids coming, or kids leaving a classroom).
Every time a kid is leaving/coming a classroom, the event is recorder in both tables: classroom_kids
and classroom_changes
 */
@Repository
public class ClassRoomPersonsRepository extends SimpleReactiveCassandraRepository<ClassRoomPersons, ClassRoomPersonsKey> {

    private final ReactiveCassandraOperations cassandraTemplate;
    private final Session session;
    private final PreparedStatementCache cache = PreparedStatementCache.create();

    public ClassRoomPersonsRepository(
            Session session,
            CassandraEntityInformation entityInformation,
            ReactiveCassandraOperations cassandraTemplate) {
        super(entityInformation, cassandraTemplate);
        this.session = session;
        this.cassandraTemplate = cassandraTemplate;
    }

    public void insertPersonInClassRoom(String classRoomId, LocalDate snapShotDate, String personId) {

        //1 get previous change date for the classrommId (you could trigger many selects starting from the current year, previous year and so on
        //2 get all previous persons from the classroma (select * from classroom_persons where classroomId and snapshot date
        //3 add new persons to the previous set of persons and insert into the classroom  and also add the change in classroom_changes
        BatchStatement batch = new BatchStatement(BatchStatement.Type.LOGGED).
                add(insertPersonInClassRoomQuery(classRoomId, snapShotDate, personId))
                .add(insertAChangeInClassRoomChanges(classRoomId, snapShotDate));
        session.execute(batch);

    }


    private BoundStatement insertPersonInClassRoomQuery(String classRoomId, LocalDate snapShotDate, String personId) {
        return CachedPreparedStatementCreator.of(
                cache,
                insertInto("classroom_persons").
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

    private BoundStatement insertAChangeInClassRoomChanges(String classRoomId, LocalDate date) {
        return CachedPreparedStatementCreator.of(
                cache,
                insertInto("classroom_changes").
                        value("id_classroom", bindMarker("id_classroom")).
                        value("year", bindMarker("year")).
                        value("month", bindMarker("month")).
                        value("day", bindMarker("day"))
        )
                .createPreparedStatement(session)
                .bind()
                .setString("id_classroom", classRoomId)
                .setInt("year", date.getYear())
                .setInt("month", date.getMonthValue())
                .setInt("day", date.getDayOfMonth());
    }

    private com.datastax.driver.core.LocalDate toCqlDate(LocalDate date) {
        return com.datastax.driver.core.LocalDate.fromYearMonthDay(
                date.getYear(), date.getMonth().getValue(), date.getDayOfMonth());
    }

}
