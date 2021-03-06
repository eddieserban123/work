package com.report.configuration;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.report.entity.Person;
import com.report.entity.classroom.ClassRoom;
import com.report.entity.classroompersons.ClassRoomPersons;
import com.report.parsers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfig {

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper mapper =  new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule module =
                new SimpleModule("CustomClassRoomDeSerializer", new Version(1, 0, 0, null, null, null));
        module.addDeserializer(ClassRoom.class, new ClassRoomDeserializer());
        module.addSerializer(ClassRoom.class, new ClassRoomSerializer());
        module.addSerializer(Person.class, new PersonSerializer());
        module.addDeserializer(Person.class, new PersonDeserializer());
        module.addDeserializer(ClassRoomPersons.class, new ClassRooomPersonsDeserializer());

        mapper.registerModule(module);
        return mapper;
    }


}
