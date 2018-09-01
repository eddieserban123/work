package com.demo.folder.tata.cache;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.demo.folder.tata.fetcher.parser.mfiseriesname.MarketData;
import com.demo.folder.tata.fetcher.parser.mfiseriesname.MfiSeriesName;
import com.demo.folder.tata.fetcher.parser.mfiseriesname.TranslateSymbol;

public enum CacheData {
    INSTANCE;
    private Map<String, Map<LocalDate, MfiSeriesName>> mfiByDates;

    CacheData() {
        mfiByDates = new ConcurrentHashMap<>();
    }


    public void addTranslateSymbol(String mfi, LocalDate date, TranslateSymbol trSymbol) {
        Map<LocalDate, MfiSeriesName> aux = new ConcurrentHashMap<>();
        MfiSeriesName mfiSeries = new MfiSeriesName(trSymbol, null);
        aux.put(date, mfiSeries);
        mfiByDates.merge(mfi, aux, (oldValue, newValue) -> {

            oldValue.merge(date, mfiSeries, (oldMfi, newMfi) ->
            {
                oldMfi.setTrSymbol(trSymbol);
                return oldMfi;
            });
            return oldValue;
        });
    }

    public void addMarketData(String mfi, LocalDate date, List<MarketData> mktData) {
        Map<LocalDate, MfiSeriesName> aux = new ConcurrentHashMap<>();
        MfiSeriesName mfiSeriesName = new MfiSeriesName(null, mktData);

        aux.put(date, mfiSeriesName);
        mfiByDates.merge(mfi, aux, (oldValue, newValue) -> {
            oldValue.merge(date, mfiSeriesName, (oldMfi, newMfi) ->
            {
                oldMfi.setMktData(mktData);
                return oldMfi;
            });
            return oldValue;
        });
    }

    @Override
    public String toString() {
        return "CacheData{" +
                "mfiByDates=" + mfiByDates +
                '}';
    }
}
