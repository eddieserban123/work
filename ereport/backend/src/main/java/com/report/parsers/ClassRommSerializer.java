package com.report.parsers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.report.entity.classroom.ClassRoom;
import com.report.entity.imageroom.ImageRoom;

import java.io.IOException;
import java.util.Base64;

public class ClassRommSerializer extends StdSerializer<ClassRoom> {


    public ClassRommSerializer() {
        this(null);
    }

    public ClassRommSerializer(Class<ClassRoom> t) {
        super(t);
    }

    @Override
    public void serialize(ClassRoom imageRoom, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", imageRoom.getKey().getId());
        jsonGenerator.writeStringField("year_month", imageRoom.getKey().getYear_month());
        jsonGenerator.writeNumberField("capacity", imageRoom.getCapacity());
        jsonGenerator.writeStringField("roomNumber", imageRoom.getRoomNumber());
        jsonGenerator.writeStringField("description", imageRoom.getDescription());
        jsonGenerator.writeEndObject();
    }
}
