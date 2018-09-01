package com.demo.folder.tata.fetcher.parser.deserializer;

import com.demo.folder.tata.config.mfi.MfiSeriesNameEndPoints;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;


public class MfiSeriesNameEndPointsDeserializer extends StdDeserializer<MfiSeriesNameEndPoints> {

    public MfiSeriesNameEndPointsDeserializer(Class<MfiSeriesNameEndPoints> vc) {
        super(vc);
    }

    public MfiSeriesNameEndPointsDeserializer() {
        this(null);
    }

    @Override
    public MfiSeriesNameEndPoints deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);




        return new MfiSeriesNameEndPoints();

    }
}
