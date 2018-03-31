package com.aqr.tca.model.serializer;

import com.aqr.tca.model.PersistToWebService;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class PersistToWebServiceSerializer extends StdSerializer<PersistToWebService> {

    private ObjectMapper mapper = new ObjectMapper();

    public PersistToWebServiceSerializer(Class<PersistToWebService> t) {
        super(t);
    }

    @Override
    public void serialize(PersistToWebService persistToWebService, JsonGenerator jgen, SerializerProvider serializers) throws IOException {


        jgen.writeStartObject();
        jgen.writeStringField("name", persistToWebService.getLocation().toString());
        jgen.writeEndObject();
    }
}
