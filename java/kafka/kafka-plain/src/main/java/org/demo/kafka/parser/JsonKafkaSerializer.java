package org.demo.kafka.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;


@AllArgsConstructor
@Builder
@Log4j2
public class JsonKafkaSerializer<T> implements Serializer<T> {

    private ObjectMapper mapper = new ObjectMapper();
    private Class<T> serializedClass;

    public JsonKafkaSerializer(Class<T> serializedClass) {
        this(serializedClass, null);
    }

    public JsonKafkaSerializer(Class<T> serializedClass, StdSerializer<T> serializer) {
        this.serializedClass = serializedClass;
        if (serializer != null) {
            SimpleModule module = new SimpleModule();
            module.addSerializer(serializedClass, serializer);
            mapper.registerModule(module);
        }
    }


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, T data) {
        try {
            byte json[] = mapper.writeValueAsBytes(data);
            log.info("Produce {}", new String(json));
            return json;
        } catch (JsonProcessingException e) {
            log.error("Unable to serialize object {}", data, e);
            return null;
        }
    }

    @Override
    public void close() {
    }
}