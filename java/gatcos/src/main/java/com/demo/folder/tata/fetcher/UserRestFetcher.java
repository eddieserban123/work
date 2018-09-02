package com.demo.folder.tata.fetcher;

import com.demo.folder.tata.fetcher.data.Data;
import com.demo.folder.tata.fetcher.data.StringData;
import com.demo.folder.tata.fetcher.data.UserData;
import com.demo.folder.tata.fetcher.params.RestParam;
import com.demo.folder.tata.fetcher.parser.beans.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.demo.folder.tata.fetcher.parser.JsonParser.JSONPARSER;

public class UserRestFetcher extends RestFetcher {

    StringData data;

    public UserRestFetcher(RestParam params) {
        super(params);
    }

    @Override
    public Data fetch() {
        data = (StringData) super.fetch();
        List<User> users = new ArrayList<>();
        try {
            users = JSONPARSER.getMapper().readValue(data.getData(),
                    JSONPARSER.getMapper().getTypeFactory().constructCollectionType(List.class, User.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new UserData(users);
    }


    @Override
    public boolean validate(Data data1) {
        boolean isSuccess = false;
        try {
         //   JSONPARSER.getMapper().writeValueAsString()
            JsonNode schemaNode = JsonLoader.fromFile(new File("/home/eddie/users.validate"));
            JsonNode node = JsonLoader.fromString(data.getData());
            JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
            JsonSchema schema = factory.getJsonSchema(schemaNode);
            ProcessingReport report = schema.validate(node);
            isSuccess = report.isSuccess();
            if (!isSuccess) {
                System.out.println("Validation data report: " + report.toString().replaceAll("\n", " | "));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ProcessingException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}
