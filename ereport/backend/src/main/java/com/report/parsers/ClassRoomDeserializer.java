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
        classroom.setRoomNumber(classNode.get("room_number").textValue());
        classroom.setPicture_id(classNode.get("picture_id").textValue());
        classroom.setDescription(classNode.get("description").textValue());
        classroom.setCapacity(classNode.get("capacity").intValue());

        return classroom;


    }
}
