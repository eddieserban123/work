package com.demo.fetcher.httpclient;

import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

public abstract class Client {
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    static {
        try {
            Properties props = new Properties();
            props.load(Client.class.getClassLoader().getResourceAsStream("kerberos.properties"));
            props.forEach((pName, pValue) -> {
                System.setProperty(pName.toString(), pValue.toString());
                LOGGER.debug("Set Kerberos system property {}: {}", pName, pValue);
            });
        } catch (IOException e) {
            LOGGER.error("Failed to load kerberos.properties due to {}: {}",
                    e.getClass().getSimpleName(), e.getMessage());
        }
    }


    public Response executeGetRequest(String url, Map<String, String> queryParams) throws IOException {
        long startTime = System.currentTimeMillis();
        Response resp = executeGetRequestT(url, queryParams);
        long duration = System.currentTimeMillis() - startTime;
        resp.setDuration(duration);
        return resp;
    }

    protected abstract Response executeGetRequestT(String url, Map<String, String> queryParams) throws IOException;

    public abstract Response executePostRequest(String url, HttpEntity httpEntity) throws IOException;

    public Response executeGetRequestT(String url) throws IOException {
        return executeGetRequestT(url, ImmutableMap.of());
    }

    protected String encodeGetUrlWithParams(String url, Map<String, String> queryParams) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder(url);
        if (!queryParams.isEmpty()) {
            sb.append("?");
            for (Map.Entry<String, String> queryParam : queryParams.entrySet()) {
                sb.append(queryParam.getKey())
                        .append("=")
                        .append(URLEncoder.encode(queryParam.getValue(), StandardCharsets.UTF_8.name()))
                        .append("&");
            }
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }

    public abstract void close();
}
