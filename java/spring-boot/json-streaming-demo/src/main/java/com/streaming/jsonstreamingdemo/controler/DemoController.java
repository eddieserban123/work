package com.streaming.jsonstreamingdemo.controler;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.streaming.jsonstreamingdemo.model.Document;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DemoController {


    public static final String DOC_LANGUAGE = "docLanguage";
    public static final String PARENT_DOC_ID = "parentDocId";
    public static final String DOC_TITLE = "docTitle";
    public static final String IS_PARENT = "isParent";
    public static final String DOC_AUTHOR = "docAuthor";
    public static final String DOC_TYPE = "docType";
    public static final String DOCUMENT_ID = "documentId";

    @GetMapping(value = "/customers")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @ResponseBody
    @RequestMapping(path = "fileupload", method = RequestMethod.POST, consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void fileUpload(HttpServletRequest request) throws IOException {

        JsonFactory jsonfactory = new JsonFactory(); //init factory
        try {
            int numberOfRecords = 0;
            JsonParser jsonParser = jsonfactory.createParser(request.getInputStream());
            Document document = new Document();
            JsonToken jsonToken = jsonParser.nextToken();
            while (jsonToken != JsonToken.END_ARRAY) { //Iterate all elements of array
                String fieldname = jsonParser.getCurrentName(); //get current name of token
                if (DOCUMENT_ID.equals(fieldname)) {
                    jsonToken = jsonParser.nextToken(); //read next token
                    document.setDocumentId(Integer.parseInt(jsonParser.getText()));
                }
                if (DOC_TYPE.equals(fieldname)) {
                    jsonToken = jsonParser.nextToken();
                    document.setDocType(jsonParser.getText());
                }
                if (DOC_AUTHOR.equals(fieldname)) {
                    jsonToken = jsonParser.nextToken();
                    document.setDocAuthor(jsonParser.getText());
                }
                if (DOC_TITLE.equals(fieldname)) {
                    jsonToken = jsonParser.nextToken();
                    document.setDocTitle(jsonParser.getText());
                }
                if (IS_PARENT.equals(fieldname)) {
                    jsonToken = jsonParser.nextToken();
                    document.setParent(jsonParser.getBooleanValue());
                }
                if (PARENT_DOC_ID.equals(fieldname)) {
                    jsonToken = jsonParser.nextToken();
                    document.setParentDocId(jsonParser.getIntValue());
                }
                if (DOC_LANGUAGE.equals(fieldname)) {  //array type field
                    jsonToken = jsonParser.nextToken();
                    List<String> docLangs = new ArrayList<>(); //read all elements and store into list
                    while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                        docLangs.add(jsonParser.getText());
                    }
                    document.setDocLanguage(docLangs);
                }
                if (jsonToken == JsonToken.END_OBJECT) {
                    //do some processing, Indexing, saving in DB etc..
                    document = new Document();
                    numberOfRecords++;
                }
                jsonToken = jsonParser.nextToken();
            }
            System.out.println("Total Records Found : " + numberOfRecords);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}