package com.demo.folder.tata.fetcher.parser.deserializer;

import com.demo.folder.tata.fetcher.parser.beans.Address;
import com.demo.folder.tata.fetcher.parser.beans.Geo;
import com.demo.folder.tata.fetcher.parser.beans.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class AddressDeserializer extends StdDeserializer<Address> {

    public AddressDeserializer() {
        this(null);
    }

    public AddressDeserializer(Class<?> vc) {
        super(vc);
    }


    @Override
    public Address deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        String street = node.get("street").asText();
        String suite = node.get("suite").asText();
        String city = node.get("city").asText();
        String zipcode = node.get("zipcode").asText();

        JsonParser parser = node.get("geo").traverse();
        parser.setCodec(oc);
        Geo geo = parser.readValueAs(Geo.class);

        Address address = new Address();
        address.setCity(city);
        address.setStreet(street);
        address.setZipcode(zipcode);
        address.setGeo(geo);
        address.setSuite(suite);

        return address;
    }
}
