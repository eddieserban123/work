package org.kafka.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.kafka.bean.Share;

import java.time.LocalDateTime;

public enum Converter {

    INSTANCE;

    private  ObjectMapper mapper = new ObjectMapper();
    Converter() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Share.class, new ShareSerialization());
        mapper.registerModule(simpleModule);
    }

    public String convert(Share object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Share share = new Share("ADBE", 100L, LocalDateTime.now());
        System.out.println(Converter.INSTANCE.convert(share));

    }
}
