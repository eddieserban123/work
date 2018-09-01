package com.demo.folder.tata.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.demo.folder.tata.config.mfi.RestEndPoints;
import com.demo.folder.tata.config.mfi.UrlResolver;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public enum AppConfig {

    APPCONFIG;

    private Config conf;

    private RestEndPoints mfi;


    AppConfig() {
        conf = ConfigFactory.load();
        readRestEndPoints();


    }


    public RestEndPoints getRestEndPoint() {
        return mfi;
    }


    private void readRestEndPoints() {

        mfi = new RestEndPoints()
                .setRestServices(
                        extractUrlResolver(
                                "config.usersEndPoints.users"));

    }

    @SuppressWarnings("unchecked")
    private UrlResolver extractUrlResolver(String queryPath) {
        Map<String, Object> treeRes = conf.getObject(queryPath).unwrapped();
        URI uri = null;
        String type = null;
        String body = null;
        Map<String, String> httpHeaders = null;
        List<String> queryParams = null;
        try {
            uri = new URI((String) treeRes.get("url"));
            type = (String) treeRes.get("type");
            List<Map<String, Object>> headers = (List<Map<String, Object>>) treeRes.get("httpheaders");
            httpHeaders = headers.stream().map(v -> v.entrySet().iterator().next())
                    .collect(Collectors.toMap(val -> val.getKey(), val -> (String) val.getValue()));
            body = (String) treeRes.get("body");
            queryParams = (List<String>) treeRes.get("queryparams");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return new UrlResolver().setUri(uri).setBody(body).setType(type).setHttpHeaders(httpHeaders)
                .setQueryParams(queryParams);

    }

}