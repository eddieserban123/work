package com.report.entity.classroomchanges;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@PrimaryKeyClass
public class ClassRoomChangesKey implements Serializable {

    @PrimaryKeyColumn(name = "id_classroom", ordinal = 0,
            type = PrimaryKeyType.PARTITIONED)
    private String id;

    @PrimaryKeyColumn(name = "year", ordinal = 1,
            type = PrimaryKeyType.PARTITIONED)
    private Integer year;


    @PrimaryKeyColumn(name = "month", ordinal = 2, type = PrimaryKeyType.CLUSTERED,
            ordering = Ordering.DESCENDING)
    private Integer month;


    @PrimaryKeyColumn(name = "day", ordinal = 3, type = PrimaryKeyType.CLUSTERED,
            ordering = Ordering.DESCENDING)
    private Integer day;



}