package com.example.kafka.config;


import com.example.kafka.config.jackson.GreetingDeserializer;
import com.example.kafka.config.jackson.GreetingsSerializer;
import com.example.kafka.pojo.Greeting;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    ObjectMapper getObjMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module =
                new SimpleModule("CustomGreetingDeserializer",
                        new Version(1, 0, 0, null, null, null));
        module.addDeserializer(Greeting.class, new GreetingDeserializer(null));
        module.addSerializer(Greeting.class, new GreetingsSerializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }

}