package com.demo.fetcher;


import com.demo.fetcher.data.Data;
import com.demo.fetcher.data.MarketFetcherData;
import com.demo.fetcher.data.StringData;
import com.demo.fetcher.params.RestParam;
import com.demo.fetcher.parser.mfiseriesname.MarketData;
import com.google.common.collect.ImmutableList;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import static com.demo.fetcher.parser.JsonParser.JSONPARSER;


public class MarketDataRestFetcher extends RestFetcher{
    public MarketDataRestFetcher(RestParam params) {
        super(params);
    }

    @Override
    public Data fetch() {
        StringData data = (StringData) super.fetch();
        JSONObject jobj = new JSONObject(data.getData());


        List<MarketData> marketData = ImmutableList.of();

        try {
            marketData = JSONPARSER.getMapper().readValue(jobj.getJSONArray("returnData").toString(),
                    JSONPARSER.getMapper().getTypeFactory().constructCollectionType(List.class, MarketData.class));
        } catch (IOException e1) {
            e1.printStackTrace();
        }



        return new MarketFetcherData(marketData);
    }
}
