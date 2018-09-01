package com.demo.folder.tata.fetcher.httpclient;

import com.demo.folder.tata.fetcher.parser.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

public class Response {
    private static final Logger LOGGER = LoggerFactory.getLogger(Response.class);
    private int status;
    private String body;
    private long duration;

    public Response(int status, String body) {
        this.status = status;
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, Object> asJsonMap() {
        try {
            ObjectMapper mapper = JsonParser.JSONPARSER.getMapper();
            return mapper.readValue(new StringReader(body), Map.class);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }


    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", body='" + body + '\'' +
                '}';
    }
}
