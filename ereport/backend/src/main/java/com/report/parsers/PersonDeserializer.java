package com.report.parsers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.report.entity.Person;
import com.report.entity.classroom.ClassRoomKey;
import com.report.util.AppDateFormatter;

import java.io.IOException;
import java.time.LocalDate;

public class PersonDeserializer extends StdDeserializer<Person> {


    public PersonDeserializer() {
        this(null);
    }

    public PersonDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Person deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode classNode = jsonParser.getCodec().readTree(jsonParser);
        Person person = new Person();

        person.setId(classNode.get("id").textValue());
        if (classNode.get("firstName") != null) {
            person.setFirstName(classNode.get("firstName").textValue());
        }

        person.setId(classNode.get("id").textValue());
        if (classNode.get("lastName") != null) {
            person.setLastName(classNode.get("lastName").textValue());
        }

        if (classNode.get("birth") != null) {
            person.setBirth(LocalDate.parse(classNode.get("birth").textValue(), AppDateFormatter.getDateFormatter()));
        }

        return person;


    }
}
