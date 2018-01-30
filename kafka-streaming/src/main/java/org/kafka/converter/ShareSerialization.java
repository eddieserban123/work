package org.kafka.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.kafka.bean.Share;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ShareSerialization  extends JsonSerializer<Share> {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");


    @Override
    public void serialize(Share share, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", share.getName());
        jsonGenerator.writeNumberField("price", share.getPrice());
        jsonGenerator.writeStringField("time", share.getTime().format(formatter));
        jsonGenerator.writeEndObject();
    }
}
