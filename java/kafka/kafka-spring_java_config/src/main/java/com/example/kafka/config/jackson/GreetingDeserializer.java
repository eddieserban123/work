package com.example.kafka.config.jackson;

import com.example.kafka.pojo.Greeting;
import com.example.kafka.producer.Producer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GreetingDeserializer extends StdDeserializer<Greeting> {

    private static final Logger logger = LoggerFactory.getLogger(GreetingDeserializer.class);

    public GreetingDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Greeting deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        logger.info("My Deserializer ************************************************");
        JsonNode node = jp.getCodec().readTree(jp);
        String name = node.get("name").asText();
        String message = node.get("msg").asText();

      return new Greeting(name, message);
    }
}
