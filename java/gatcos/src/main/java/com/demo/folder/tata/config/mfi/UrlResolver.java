package com.demo.folder.tata.config.mfi;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlResolver {

    private URI uri;
    private String type;
    private Map<String, String> httpHeaders = new HashMap<>();
    private List<String> queryParams = new ArrayList<>();


    private String body;


    public UrlResolver() {
    }

    public URI getUri() {
        return uri;
    }

    public UrlResolver setUri(URI uri) {
        this.uri = uri;
        return this;
    }

    public String getType() {
        return type;
    }

    public UrlResolver setType(String type) {
        this.type = type;
        return this;
    }

    public Map<String, String> getHttpHeaders() {
        return httpHeaders;
    }

    public UrlResolver setHttpHeaders(Map<String, String> httpHeaders) {
        this.httpHeaders = httpHeaders;
        return this;
    }

    public String getBody() {
        return body;
    }

    public UrlResolver setBody(String body) {
        this.body = body;
        return this;
    }

    public List<String> getQueryParams() {
        return queryParams;
    }

    public UrlResolver setQueryParams(List<String> queryParams) {
        this.queryParams = queryParams;
        return this;
    }

}
