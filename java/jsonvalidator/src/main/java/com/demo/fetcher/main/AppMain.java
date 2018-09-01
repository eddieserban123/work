package com.demo.fetcher.main;

import com.demo.fetcher.data.HolidayCalendarData;
import com.demo.fetcher.data.MarketFetcherData;
import com.demo.fetcher.data.TranslateSymbolData;
import com.demo.fetcher.util.BuildRestFetcher;
import com.demo.util.AqrUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.demo.config.AppConfig.APPCONFIG;
import static com.demo.fetcher.util.BuildRestFetcher.extractFuture;
import static com.demo.fetcher.util.BuildRestFetcher.getMarketData;
import static com.demo.fetcher.util.BuildRestFetcher.getTranslateSymbol;
import static java.util.stream.Collectors.toList;

public class AppMain {

    public static void main(String[] args) {
        //read series name from config list

        String startTime = APPCONFIG.getStartTime();
        String stopTime = APPCONFIG.getStopTime();

        List<String> mfiSeriesName = APPCONFIG.getMfiSeriesName();
        List<String> bbCodes = retrieveBBCodes(mfiSeriesName);


        List<LocalDate> rangeDates = AqrUtils.getDatesBetween2Dates(startTime, stopTime);
        String bloombergCode = String.join(",", mfiSeriesName).replace('_', ' ');

        ExecutorService executor = Executors.newFixedThreadPool(6); //TODO should be configurable

        //1 RETREIVING TRANSLATE SYMBOLS
        List<CompletableFuture<TranslateSymbolData>> trSymbols = rangeDates.stream().map(
                date -> CompletableFuture.supplyAsync(
                        () -> getTranslateSymbol(date, bloombergCode), executor)).
                collect(toList());

        CompletableFuture<List<TranslateSymbolData>> data1 = extractFuture(trSymbols);
        data1.join().stream().map(t -> t.getData()).forEach(System.out::println);
        //2 GET HOLIDAY CALENDAR
        List<CompletableFuture<HolidayCalendarData>> holidaysFut = bbCodes.stream().map(
                bbcode -> CompletableFuture.supplyAsync(
                        () -> BuildRestFetcher.getHolidayCalendar(bbcode), executor)).
                collect(toList());

        CompletableFuture<List<HolidayCalendarData>> data2 = extractFuture(holidaysFut);
        data2.join().stream().map(h -> h.getData()).forEach(System.out::println);

        //3 GET MARKET DATA for all mfi and for all dates

        List<CompletableFuture<MarketFetcherData>> marketDataFut = mfiSeriesName.stream().map(
                mfi -> CompletableFuture.supplyAsync(
                        () -> getMarketData(mfi, startTime, stopTime), executor)).
                collect(toList());

        CompletableFuture<List<MarketFetcherData>> data3 = extractFuture(marketDataFut).thenApply(
                marketFetcherData -> marketFetcherData);
        data3.join().stream().map(h -> h.getData()).forEach(System.out::println);


    }

    private static List<String> retrieveBBCodes(List<String> mfiSeriesName) {
        //TODO  for the momment leave it as it is in the current GAATCOSt
        return mfiSeriesName.stream().map(mfi -> mfi.replace('_', ' ')).collect(toList());
    }


}
