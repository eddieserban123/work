package com.report.parsers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.report.entity.Person;
import com.report.util.AppDateFormatter;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import static com.report.util.AppDateFormatter.getDateFormatter;

public class PersonSerializer extends StdSerializer<Person> {

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
        jsonGenerator.writeStringField("firstName", person.getFirstName());
        jsonGenerator.writeStringField("lastName", person.getLastName());
        jsonGenerator.writeStringField("birth", person.getBirth().format(getDateFormatter()));
        jsonGenerator.writeEndObject();
    }
}
