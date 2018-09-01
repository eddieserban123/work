package com.demo.folder.tata.fetcher.parser.deserializer;

import com.demo.folder.tata.fetcher.parser.beans.Address;
import com.demo.folder.tata.fetcher.parser.beans.Geo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class GeoDeserializer extends StdDeserializer<Geo> {

    public GeoDeserializer() {
        this(null);
    }

    public GeoDeserializer(Class<?> vc) {
        super(vc);
    }


    @Override
    public Geo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        String lat = node.get("lat").asText();
        String lng = node.get("lng").asText();

        Geo geo = new Geo();
        geo.setLat(lat);
        geo.setLng(lng);
        return geo;
    }
}
