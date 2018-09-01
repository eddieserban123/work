package com.demo.fetcher.params;

import com.amazonaws.http.HttpMethodName;

import java.net.URI;
import java.util.Map;

public class RestParam implements GeneralParams {
    private URI uri;
    private HttpMethodName httpMethod;
    private Map<String, String> headers;


    private Map<String, String> queryParams;
    private String body;



    public RestParam() {
    }

    public RestParam(URI uri, HttpMethodName httpMethod, Map<String, String> headers, String body) {
        this.uri = uri;
        this.httpMethod = httpMethod;
        this.headers = headers;
        this.body = body;
    }

    public URI getUri() {
        return uri;
    }

    public RestParam setUri(URI uri) {
        this.uri = uri;
        return this;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public RestParam setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
        return this;
    }


    public HttpMethodName getHttpMethod() {
        return httpMethod;
    }

    public RestParam setHttpMethod(HttpMethodName httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public RestParam setHttpMethod(String httpMethod) {
        this.httpMethod = HttpMethodName.fromValue(httpMethod);
        return this;
    }


    public Map<String, String> getHeaders() {
        return headers;
    }

    public RestParam setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public String getBody() {
        return body;
    }

    public RestParam setBody(String body) {
        this.body = body;
        return this;
    }

    @Override
    public String getIdentifier() {
        return uri.toString();
    }
}
