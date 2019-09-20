package com.report.parsers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.report.entity.Person;
import com.report.entity.classroomkids.ClassRoomKids;
import com.report.entity.classroomkids.ClassRoomKidsKey;
import com.report.util.AppDateFormatter;

import java.io.IOException;
import java.time.LocalDate;

public class ClassRooomKidsDeserializer extends StdDeserializer<ClassRoomKids> {


    public ClassRooomKidsDeserializer() {
        this(null);
    }

    public ClassRooomKidsDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ClassRoomKids deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode classNode = jsonParser.getCodec().readTree(jsonParser);
        ClassRoomKids classRoomKids = new ClassRoomKids();
        ClassRoomKidsKey key = new ClassRoomKidsKey();

        if (classNode.get("id_classroom") != null) {
            key.setId_classroom(classNode.get("id_classroom").textValue());
        }

        if (classNode.get("snapshot_date") != null) {
            key.setSnapshot_date(LocalDate.parse(classNode.get("snapshot_date").textValue(), AppDateFormatter.getDateFormatter()));
        }

        classRoomKids.setKey(key);

        if (classNode.get("person_id") != null) {
            classRoomKids.setPersonId(classNode.get("person_id").textValue());
        }


        return classRoomKids;


    }
}
