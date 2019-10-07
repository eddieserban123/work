package com.report.entity.classroomchanges;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table("classroom_changes")
public class ClassRoomChanges {

    @PrimaryKey
    private ClassRoomChangesKey key;

    public ClassRoomChanges(String idClassRoom, Integer year, Integer month, Integer day) {
        this(new ClassRoomChangesKey(idClassRoom, year, month, day));
    }

}
