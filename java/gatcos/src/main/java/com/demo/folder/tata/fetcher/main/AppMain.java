package com.demo.folder.tata.fetcher.main;

import com.demo.folder.tata.fetcher.data.UserData;
import com.demo.folder.tata.fetcher.parser.beans.User;
import com.demo.folder.tata.fetcher.util.BuildRestFetcher;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.stream.Collectors.toList;

public class AppMain {

    public static class SimpleBean
    {
        private int property1;
        private String property2;
        private String[] property3;
        private Collection<Float> property4;
        @JsonProperty(required = true)
        private String property5;

        public int getProperty1() {
            return property1;
        }

        public void setProperty1(int property1) {
            this.property1 = property1;
        }

        public String getProperty2() {
            return property2;
        }

        public void setProperty2(String property2) {
            this.property2 = property2;
        }

        public String[] getProperty3() {
            return property3;
        }

        public void setProperty3(String[] property3) {
            this.property3 = property3;
        }

        public Collection<Float> getProperty4() {
            return property4;
        }

        public void setProperty4(Collection<Float> property4) {
            this.property4 = property4;
        }

        public String getProperty5() {
            return property5;
        }

        public void setProperty5(String property5) {
            this.property5 = property5;
        }
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

        ObjectMapper MAPPER = new ObjectMapper();
        JsonSchemaGenerator generator = new JsonSchemaGenerator(MAPPER);
        JsonSchema jsonSchema = generator.generateSchema(User.class);


        Files.write(Paths.get("/home/eddie/user.validate"),MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema).getBytes());


        //System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema));
        //read series name from config list


//        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
//        try {
//            JsonNode schemaFromDisk = JsonLoader.fromFile(new File("/home/eddie/user.validate"));
//            mapper.acceptJsonFormatVisitor(User.class, visitor);
//        } catch (IOException e ) {
//            e.printStackTrace();
//        }


        ExecutorService executor = Executors.newFixedThreadPool(6); //TODO should be configurable

        //1 RETREIVING USERS
        CompletableFuture<UserData> users =
                CompletableFuture.supplyAsync(() -> BuildRestFetcher.getUsers(), executor);
        UserData data = users.get();
        data.getData().stream().forEach(System.out::println);







    }

    private static List<String> retrieveBBCodes(List<String> mfiSeriesName) {
        //TODO  for the momment leave it as it is in the current GAATCOSt
        return mfiSeriesName.stream().map(mfi -> mfi.replace('_', ' ')).collect(toList());
    }


}
