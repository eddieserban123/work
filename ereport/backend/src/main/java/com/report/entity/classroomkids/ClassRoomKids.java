package com.report.entity.classroomkids;


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
@Table("classroom_kids")
public class ClassRoomKids {

    @PrimaryKey
    private ClassRoomKidsKey key;

    public ClassRoomKids(ClassRoomKidsKey key) {
        this.key = key;
    }

    public ClassRoomKids(String id_classroom, LocalDate snapshot_date) {
        this(new ClassRoomKidsKey(id_classroom, snapshot_date));
    }

    @Column("person_id")
    private String personId;

}
