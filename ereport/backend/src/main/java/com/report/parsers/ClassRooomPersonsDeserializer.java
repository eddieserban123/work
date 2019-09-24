package com.report.parsers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.report.entity.classroompersons.ClassRoomPersons;
import com.report.entity.classroompersons.ClassRoomPersonsKey;
import com.report.util.AppDateFormatter;

import java.io.IOException;
import java.time.LocalDate;

public class ClassRooomPersonsDeserializer extends StdDeserializer<ClassRoomPersons> {


    public ClassRooomPersonsDeserializer() {
        this(null);
    }

    public ClassRooomPersonsDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ClassRoomPersons deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode classNode = jsonParser.getCodec().readTree(jsonParser);
        ClassRoomPersons classRoomPersons = new ClassRoomPersons();
        ClassRoomPersonsKey key = new ClassRoomPersonsKey();

        if (classNode.get("id_classroom") != null) {
            key.setId_classroom(classNode.get("id_classroom").textValue());
        }

        if (classNode.get("snapshot_date") != null) {
            key.setSnapshot_date(LocalDate.parse(classNode.get("snapshot_date").textValue(), AppDateFormatter.getDateFormatter()));
        }

        classRoomPersons.setKey(key);

        if (classNode.get("person_id") != null) {
            classRoomPersons.setPersonId(classNode.get("person_id").textValue());
        }


        return classRoomPersons;


    }
}
