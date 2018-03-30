package com.aqr.tca.config;

import com.aqr.tca.model.Person;
import com.aqr.tca.model.serializer.PersonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Collections;
import java.util.Properties;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;
import static com.fasterxml.jackson.databind.SerializationFeature.WRAP_ROOT_VALUE;

@Configuration
public class SpringRootConfig {

    @Autowired
    private ApplicationContext context;

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        final ObjectMapper mapper = new ObjectMapper();

        mapper.enable(READ_UNKNOWN_ENUM_VALUES_AS_NULL);
        mapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(WRAP_ROOT_VALUE);
        SimpleModule mod = new SimpleModule("SWEngineer Module");

        // Add the custom serializer to the module
        mod.addSerializer(new PersonSerializer(Person.class));
        mapper.registerModule(mod);    // Register the module on the mapper
        return mapper;
    }

    @Bean(name = "jacksonConverter")
    public MappingJackson2HttpMessageConverter jacksonConverter(final ObjectMapper objectMapper) {
        final MappingJackson2HttpMessageConverter httpMessageConverter = new MappingJackson2HttpMessageConverter();
        httpMessageConverter.setObjectMapper(objectMapper);
        return httpMessageConverter;
    }




}
