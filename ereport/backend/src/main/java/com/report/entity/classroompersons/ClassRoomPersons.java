package com.report.entity.classroompersons;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table("classroom_persons")
public class ClassRoomPersons {

    @PrimaryKey
    private ClassRoomPersonsKey key;

    public ClassRoomPersons(ClassRoomPersonsKey key) {
        this.key = key;
    }

    public ClassRoomPersons(String id_classroom, LocalDate snapshot_date) {
        this(new ClassRoomPersonsKey(id_classroom, snapshot_date));
    }

    @Column("person_id")
    private String personId;

}
