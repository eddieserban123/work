package com.demo.folder.tata.fetcher.main;

import com.demo.folder.tata.fetcher.parser.beans.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import com.google.common.collect.ImmutableMap;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.ValidationMessage;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class AppMain3 {

    public static void main(String[] args) throws Exception {

        String str = "{\"id\":1,\"name\":\"Leanne Graham\",\"username\":\"Bret\",\"email\":\"Sincere@april.biz\",\"address\":{\"street\":\"Kulas Light\",\"suite\":\"Apt. 556\",\"city\":\"Gwenborough\",\"zipcode\":\"92998-3874\",\"geo\":{\"lat\":\"-37.3159\",\"lng\":\"81.1496\"}}}";
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
        JsonSchema jsonSchema = getJsonSchemaFromStringContent(
                new String(Files.readAllBytes(Paths.get("/home/eddie/user.validate1"))));
        JsonNode jsonNode = objMapper.readTree(str);

        Set<ValidationMessage> errors = jsonSchema.validate(jsonNode);
        System.out.println("Validation errors: " + errors.size());



        NameChangingStrategy nm = new NameChangingStrategy(ImmutableMap.of("emaill","email"));
        objMapper.setPropertyNamingStrategy(nm);
        //User u = objMapper.treeToValue(jsonNode, User.class);
        User u = objMapper.readValue(str, User.class);
        System.out.println(u);
       // JsonNode jn = JsonLoader.fromFile(new File("/home/eddie/user.validate1"));
    }

    protected static JsonNode getJsonNodeFromStringContent(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(content);
        return node;
    }

    protected static JsonSchema getJsonSchemaFromStringContent(String schemaContent) throws Exception {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance();
        JsonSchema schema = factory.getSchema(schemaContent);
        return schema;
    }
}
