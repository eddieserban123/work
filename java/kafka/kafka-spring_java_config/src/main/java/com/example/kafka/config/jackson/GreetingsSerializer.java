package com.example.kafka.config.jackson;

import com.example.kafka.pojo.Greeting;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GreetingsSerializer extends StdSerializer<Greeting> {

    private static final Logger logger = LoggerFactory.getLogger(GreetingDeserializer.class);

    public GreetingsSerializer() {
        this(null);
    }

    public GreetingsSerializer(Class<Greeting> t) {
        super(t);
    }

    @Override
    public void serialize(Greeting greeting, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        logger.info("My Serializer ************************************************");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", greeting.getName());
        jsonGenerator.writeStringField("msg", greeting.getMsg());
        jsonGenerator.writeEndObject();

    }
}
