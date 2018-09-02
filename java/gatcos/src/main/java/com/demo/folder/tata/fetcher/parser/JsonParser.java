package com.demo.folder.tata.fetcher.parser;

import com.demo.folder.tata.fetcher.parser.beans.Address;
import com.demo.folder.tata.fetcher.parser.beans.Geo;
import com.demo.folder.tata.fetcher.parser.beans.User;
import com.demo.folder.tata.fetcher.parser.deserializer.AddressDeserializer;
import com.demo.folder.tata.fetcher.parser.deserializer.GeoDeserializer;
import com.demo.folder.tata.fetcher.parser.deserializer.UsersDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;
import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRAP_ROOT_VALUE;

public enum JsonParser {

    JSONPARSER;
    ObjectMapper mapper = new ObjectMapper();

    JsonParser() {

        mapper.enable(READ_UNKNOWN_ENUM_VALUES_AS_NULL);
        mapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(WRAP_ROOT_VALUE);
        mapper.configure(ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        SimpleModule mod = new SimpleModule();


        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();


        // Add the custom deserializer to the module

        mod.addDeserializer(User.class, new UsersDeserializer());
        mod.addDeserializer(Address.class, new AddressDeserializer());
        mod.addDeserializer(Geo.class, new GeoDeserializer());




        mapper.registerModule(mod);    // Register the module on the mapper

    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}
