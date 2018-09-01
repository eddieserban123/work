package com.demo.folder.tata.config;

import com.demo.folder.tata.config.mfi.UrlResolver;

public class EndPoints {
    private UrlResolver marketData;

    public EndPoints(UrlResolver marketData) {
        this.marketData = marketData;
    }

    public UrlResolver getMarketData() {
        return marketData;
    }

    public EndPoints setMarketData(UrlResolver marketData) {
        this.marketData = marketData;
        return this;
    }


}
