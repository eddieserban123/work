package com.report.parsers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.report.entity.Person;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class PersonSerializer extends StdSerializer<Person> {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");

    public PersonSerializer() {
        this(null);
    }

    public PersonSerializer(Class<Person> t) {
        super(t);
    }

    @Override
    public void serialize(Person person, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", person.getId());
        jsonGenerator.writeStringField("name", person.getName());
        jsonGenerator.writeStringField("birth", person.getBirth().format(formatter));
        jsonGenerator.writeEndObject();
    }
}
