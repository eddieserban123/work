package com.demo.folder.tata.fetcher.main;

import com.demo.folder.tata.fetcher.parser.beans.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.examples.Utils;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import java.io.File;
import java.io.IOException;

//https://github.com/java-json-tools/json-schema-validator

public class AppMain2 {

    public static void main(String[] args) throws IOException, ProcessingException {
        String str = "{\"id\":1,\"name\":\"Leanne Graham\",\"username\":\"Bret\",\"email\":\"Sincere@april.biz\",\"address\":{\"street\":\"Kulas Light\",\"suite\":\"Apt. 556\",\"city\":\"Gwenborough\",\"zipcode\":\"92998-3874\",\"geo\":{\"lat\":\"-37.3159\",\"lng\":\"81.1496\"}}}";
        ObjectMapper objMapper = new ObjectMapper();


        User u = objMapper.readValue(str, User.class);


        JsonNode jn = JsonLoader.fromFile(new File("/home/eddie/user.validate1"));

        final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();

        final com.github.fge.jsonschema.main.JsonSchema schema = factory.getJsonSchema(jn);

        ProcessingReport report;

        report = schema.validate(JsonLoader.fromString(str));
        System.out.println(report);


        System.out.println(u);

    }
}
