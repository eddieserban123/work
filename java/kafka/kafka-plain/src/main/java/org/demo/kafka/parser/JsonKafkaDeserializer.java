package org.demo.kafka.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
@Builder
@Log4j2
public class JsonKafkaDeserializer<T> implements Deserializer<T> {

    private ObjectMapper mapper = new ObjectMapper();
    private Class<T> deserializedClass;

    public JsonKafkaDeserializer(Class<T> deserializedClass) {
        this(deserializedClass, null);
    }

    public JsonKafkaDeserializer(Class<T> deserializedClass, StdDeserializer<T> stdDeserializer) {
        this.deserializedClass = deserializedClass;
        if (stdDeserializer!= null) {
            SimpleModule module = new SimpleModule();
            module.addDeserializer(deserializedClass, stdDeserializer);
            mapper.registerModule(module);
        }
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        if (deserializedClass == null) {
            deserializedClass = (Class<T>) configs.get("serializedClass");
        }
    }


    @Override
    public T deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        try {
            return mapper.readValue(data, deserializedClass);
        } catch (IOException e) {
            log.error(" Error: {}", e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public T deserialize(String topic, Headers headers, byte[] data) {
        if (data == null) {
            return null;
        }

        try {
            return mapper.readValue(data, deserializedClass);
        } catch (IOException e) {
            log.error(" Error: {}", e.getLocalizedMessage());
        }
        return null;

    }

    @Override
    public void close() {

    }
}
