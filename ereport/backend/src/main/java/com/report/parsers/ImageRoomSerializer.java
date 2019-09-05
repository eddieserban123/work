package com.report.parsers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.report.entity.imageroom.ImageRoom;

import java.io.IOException;
import java.util.Base64;

public class ImageRoomSerializer extends StdSerializer<ImageRoom> {


    public ImageRoomSerializer() {
        this(null);
    }

    public ImageRoomSerializer(Class<ImageRoom> t) {
        super(t);
    }

    @Override
    public void serialize(ImageRoom imageRoom, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        byte[] bytes = new byte[imageRoom.getContent().remaining()];
        jsonGenerator.writeBinary(Base64.getEncoder().encode(imageRoom.getContent().get(bytes, 0, bytes.length).array()));
        jsonGenerator.writeEndObject();
    }
}
