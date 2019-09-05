package com.report.entity.imageroom;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.nio.ByteBuffer;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table("image_room")
public class ImageRoom {

    @PrimaryKey
    ImageRoomKey key;

    ByteBuffer content;

}
