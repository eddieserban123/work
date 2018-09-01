package com.demo.fetcher;

import com.demo.fetcher.data.Data;
import com.demo.fetcher.data.StringData;
import com.demo.fetcher.httpclient.Client;
import com.demo.fetcher.httpclient.KerberosHttpClient;
import com.demo.fetcher.httpclient.Response;
import com.demo.fetcher.params.RestParam;

import java.io.IOException;

public class RestFetcher extends GeneralFetcher {

    protected Client httpClient = new KerberosHttpClient();

    public RestFetcher(RestParam params) {
        super(params);
    }



    @Override
    public Data fetch() {
        StringData data = null;
        RestParam  restParam = (RestParam) getParams();

        // TODO should handle http params
        Response resp = null;
        try {
            switch (restParam.getHttpMethod()) {
                case GET: {
                    resp = httpClient.executeGetRequest(
                            restParam.getUri().toString(),
                            restParam.getQueryParams()
                    );
                }
            }
            return new StringData(resp.getBody());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new StringData();
    }
}
