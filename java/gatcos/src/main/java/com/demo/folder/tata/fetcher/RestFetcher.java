package com.demo.folder.tata.fetcher;

import com.demo.folder.tata.fetcher.data.Data;
import com.demo.folder.tata.fetcher.data.StringData;
import com.demo.folder.tata.fetcher.httpclient.Client;
import com.demo.folder.tata.fetcher.httpclient.KerberosHttpClient;
import com.demo.folder.tata.fetcher.httpclient.Response;
import com.demo.folder.tata.fetcher.params.RestParam;

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
