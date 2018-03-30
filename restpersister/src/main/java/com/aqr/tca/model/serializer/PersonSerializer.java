package com.aqr.tca.model.serializer;

import com.aqr.tca.model.Person;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class PersonSerializer extends StdSerializer<Person> {

    private ObjectMapper mapper = new ObjectMapper();

    public PersonSerializer(Class<Person> t) {
        super(t);
    }

    @Override
    public void serialize(Person person, JsonGenerator jgen, SerializerProvider serializers) throws IOException {


        jgen.writeStartObject();
        jgen.writeStringField("name", person.getName());
        jgen.writeEndObject();
    }
}
