package com.report.repository;

import com.report.entity.classroomchanges.ClassRoomChanges;
import com.report.entity.classroomchanges.ClassRoomChangesKey;
import lombok.AllArgsConstructor;
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

@Repository
@AllArgsConstructor
public class ClassRoomChangesRepository {

    private ReactiveCassandraTemplate reactiveCassandraOperations;

    public Flux<ClassRoomChanges> findAllChanges(String keyspace, String table, ClassRoomChangesKey key) {
        return reactiveCassandraOperations.select( select().from(keyspace, table).
                where(eq("id_classroom", key.getId())).and((eq("year",key.getYear()))), ClassRoomChanges.class);
    }

}
