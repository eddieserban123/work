package com.aqr.tca.model;


import org.springframework.http.HttpMethod;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class PersistToWebService {

    private String fromTopic;
    private String appName; //needed for Kafka as a groupID
    private URI location;

    private HttpMethod methodType;

    private Map<String, String> headers = new HashMap<>();

    public PersistToWebService() {
    }

    public PersistToWebService(String fromTopic, String appName, URI location, HttpMethod methodType, Map<String, String> headers) {
        this.location = location;
        this.appName = appName;
        this.methodType = methodType;
        this.headers = headers;
        this.fromTopic = fromTopic;
    }

    public URI getLocation() {
        return location;
    }

    public void setLocation(URI location) {
        this.location = location;
    }

    public HttpMethod getMethodType() {
        return methodType;
    }

    public void setMethodType(HttpMethod methodType) {
        this.methodType = methodType;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getFromTopic() {
        return fromTopic;
    }

    public void setFromTopic(String fromTopic) {
        this.fromTopic = fromTopic;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
