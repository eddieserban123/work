package com.demo.fetcher.util;

import com.demo.config.mfi.UrlResolver;
import com.demo.fetcher.HolidayCalendarRestFetcher;
import com.demo.fetcher.MarketDataRestFetcher;
import com.demo.fetcher.TranslateSymbolRestFetcher;
import com.demo.fetcher.data.HolidayCalendarData;
import com.demo.fetcher.data.MarketFetcherData;
import com.demo.fetcher.data.TranslateSymbolData;
import com.demo.fetcher.params.RestParam;
import com.google.common.collect.ImmutableMap;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.demo.config.AppConfig.APPCONFIG;
import static com.demo.fetcher.util.SymbolType.BLOOMBERG;
import static java.util.stream.Collectors.toList;

public class BuildRestFetcher {

    public static TranslateSymbolData getTranslateSymbol(LocalDate date, String symbols) {

        UrlResolver ep = APPCONFIG.getMfiSeriesNameEndPoint().getTranslateSymbol();
        return ((TranslateSymbolData) new TranslateSymbolRestFetcher(buildRestParam(ep,
                ImmutableMap.of("symbol", symbols,
                        "from_symbol_type", "BLOOMBERG", "to_symbol_type", "GRP,AQRID",
                        "date", date.toString()))).getData());

    }


    public static HolidayCalendarData getHolidayCalendar(String symbols) {
        UrlResolver ep = APPCONFIG.getMfiSeriesNameEndPoint().getHolidayCalendar();
        return ((HolidayCalendarData) new HolidayCalendarRestFetcher(buildRestParam(ep,
                ImmutableMap.of("symbol", symbols, "symbol_type", BLOOMBERG.toString()))).getData());

    }

    public static MarketFetcherData getMarketData(String symbol, String startTime, String stopTime) {

        UrlResolver ep = APPCONFIG.getMfiSeriesNameEndPoint().getMarketData();
        return ((MarketFetcherData) new MarketDataRestFetcher(buildRestParam(ep,
                ImmutableMap.of("seriesName", symbol,
                        "startTime", startTime, "stopTime", stopTime))).getData());

    }


    public static <T> CompletableFuture<List<T>> extractFuture(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture =
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        return allDoneFuture.thenApply(v ->
                futures.stream().
                        map(future -> future.join()).
                        collect(toList())
        );
    }

    private static RestParam buildRestParam(UrlResolver ep, Map<String, String> queryParams) {
        return new RestParam().setUri(ep.getUri()).setHttpMethod(ep.getType()).setQueryParams(queryParams);
    }
}
