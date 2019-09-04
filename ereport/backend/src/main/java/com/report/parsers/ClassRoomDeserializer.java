package com.report.parsers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.report.entity.classroom.ClassRoom;
import com.report.entity.classroom.ClassRoomKey;

import java.io.IOException;

public class ClassRoomDeserializer extends StdDeserializer<ClassRoom> {

    public ClassRoomDeserializer() {
        this(null);
    }

    public ClassRoomDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ClassRoom deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode classNode = jsonParser.getCodec().readTree(jsonParser);
        ClassRoom classroom = new ClassRoom();
        ClassRoomKey key = new ClassRoomKey();

        key.setId(classNode.get("id").textValue());
        key.setYear_month(classNode.get("year_month").textValue());

        classroom.setKey(key);
        if (classNode.get("room_number") != null) {
            classroom.setRoomNumber(classNode.get("room_number").textValue());
        }

        if (classNode.get("description") != null) {
            classroom.setDescription(classNode.get("description").textValue());
        }
        if (classNode.get("capacity") != null) {
            classroom.setCapacity(classNode.get("capacity").intValue());
        }
        return classroom;


    }
}
