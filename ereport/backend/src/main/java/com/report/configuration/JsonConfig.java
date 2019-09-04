package com.report.configuration;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.report.entity.classroom.ClassRoom;
import com.report.parsers.ClassRoomDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfig {

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper mapper =  new ObjectMapper();
        SimpleModule module =
                new SimpleModule("CustomClassRoomDeSerializer", new Version(1, 0, 0, null, null, null));
        module.addDeserializer(ClassRoom.class, new ClassRoomDeserializer());
        mapper.registerModule(module);

        return mapper;
    }


}
