package com.report.entity.classroom;


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
@Table("classroom")
public class ClassRoom {

    @PrimaryKey
    private ClassRoomKey key;

    public ClassRoom(ClassRoomKey key) {
        this.key = key;
    }

    public ClassRoom(String id, String year_month) {
        this(new ClassRoomKey(id, year_month));
    }


    private Integer capacity;

    @Column("room_number")
    private String roomNumber;

    private String description;

}
