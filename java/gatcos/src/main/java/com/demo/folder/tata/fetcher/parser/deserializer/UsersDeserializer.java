package com.demo.folder.tata.fetcher.parser.deserializer;

import com.demo.folder.tata.fetcher.parser.beans.Address;
import com.demo.folder.tata.fetcher.parser.beans.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class UsersDeserializer extends StdDeserializer<User> {

    public UsersDeserializer() {
        this(null);
    }

    public UsersDeserializer(Class<?> vc) {
        super(vc);
    }


    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);


//        HyperSchemaFactoryWrapper userVisitor = new HyperSchemaFactoryWrapper();
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.acceptJsonFormatVisitor(User.class, userVisitor);
//        JsonSchema userSchema = userVisitor.finalSchema();
//
//
//
//        User u = com.demo.folder.tata.fetcher.parser.JsonParser.JSONPARSER.getMapper().readValue(node.asText(),User.class);
//
        Integer id = node.get("id").asInt();
        String name = node.get("name").asText();
        String username = node.get("username").asText();
        String email = node.get("email").asText();

        JsonParser parser = node.get("address").traverse();
        parser.setCodec(oc);
        Address address = parser.readValueAs(Address.class);

        String phone = node.get("phone").asText();
        String website = node.get("website").asText();

        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setUsername(username);
        user.setEmaill(email);
        user.setAddress(address);
        user.setPhone(phone);
        user.setWebsite(website);


        return user;
    }
}
