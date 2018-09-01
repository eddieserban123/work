package com.demo.fetcher.data;

import com.demo.fetcher.parser.mfiseriesname.MarketData;


import java.util.List;

public class MarketFetcherData implements Data<List<MarketData>> {

    private List<MarketData> marketData;

    public MarketFetcherData(List<MarketData> marketData) {
        this.marketData = marketData;
    }

    @Override
    public List<MarketData> getData() {
        return marketData;
    }
}
