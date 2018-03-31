package com.aqr.tca.model;


import org.springframework.http.HttpMethod;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class PersistToWebService {

    private URI location;  //chech with a URI

    private HttpMethod methodType;

    private Map<String,String> headers = new HashMap<>();

    public PersistToWebService() {
    }

    public PersistToWebService(URI location, HttpMethod methodType, Map<String, String> headers) {
        this.location = location;
        this.methodType = methodType;
        this.headers = headers;
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
}
