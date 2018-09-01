package com.demo.folder.worker;

import com.demo.folder.tata.fetcher.httpclient.Client;
import com.demo.folder.tata.fetcher.httpclient.KerberosHttpClient;
import com.demo.folder.worker.pojos.WorkStatus;

import java.util.List;

public abstract class Work {
    protected String mfiSeriesName;
    protected Client httpClient = new KerberosHttpClient();

    public Work(String mfiSeriesName) {
        this.mfiSeriesName = mfiSeriesName;
    }


    protected abstract List<WorkStatus> doWork();

}
