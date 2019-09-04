package com.report.entity.classroom;

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
public class ClassRoomKey implements Serializable {

    @PrimaryKeyColumn(name = "id", ordinal = 0,
            type = PrimaryKeyType.PARTITIONED)
    private String id;

    @PrimaryKeyColumn(name = "year_month", ordinal = 1, type = PrimaryKeyType.CLUSTERED,
            ordering = Ordering.DESCENDING)
    private String year_month;

}