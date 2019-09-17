package com.report.entity.classroomkids;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@PrimaryKeyClass
public class ClassRoomKidsKey implements Serializable {

    @PrimaryKeyColumn(name = "id_classroom", ordinal = 0,
            type = PrimaryKeyType.PARTITIONED)
    private String id_classroom;

    @PrimaryKeyColumn(name = "snapshot_date", ordinal = 1, type = PrimaryKeyType.CLUSTERED,
            ordering = Ordering.DESCENDING)
    private LocalDate snapshot_date;

}