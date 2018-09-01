package com.demo.folder.worker;

import com.demo.folder.tata.cache.CacheData;
import com.demo.folder.tata.config.AppConfig;
import com.demo.folder.tata.config.mfi.MfiSeriesNameEndPoints;
import com.demo.folder.tata.config.mfi.UrlResolver;
import com.demo.folder.tata.fetcher.httpclient.Response;
import com.demo.folder.tata.fetcher.parser.mfiseriesname.HolidayCalendar;
import com.demo.folder.tata.fetcher.parser.mfiseriesname.LiquidHours;
import com.demo.folder.tata.fetcher.parser.mfiseriesname.MarketData;
import com.demo.folder.worker.pojos.WorkStatus;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.demo.folder.tata.fetcher.parser.JsonParser.JSONPARSER;
import static com.demo.folder.util.SymbolType.BLOOMBERG;

public class SeriesNameWork extends Work {

    public SeriesNameWork(String mfiName) {
        super(mfiName);
    }

    @Override public List<WorkStatus> doWork() {
        MfiSeriesNameEndPoints ep = AppConfig.APPCONFIG.getMfiSeriesNameEndPoint();
        String startTime = AppConfig.APPCONFIG.getStartTime();
        String stopTime = AppConfig.APPCONFIG.getStopTime();
        List<WorkStatus> workStatusList = new ArrayList<>();

        // Step 1 retrieve the holidays for the mfi series
        Map.Entry<List<HolidayCalendar>, WorkStatus> holidayCalendarResult = getHolidayCalendar(ep.getHolidayCalendar());
        List<HolidayCalendar> holidays = holidayCalendarResult.getKey();
        workStatusList.add(holidayCalendarResult.getValue());
        // Step 2 retrieve LiquidHours ...
        Map.Entry<List<LiquidHours>, WorkStatus> ret = getLiquidHours(ep.getLiquidHours());
        List<LiquidHours> liquidHours = ret.getKey();
        workStatusList.add(ret.getValue());

        Map<String, List<LiquidHours>> liquidHoursBySymbol = collectTimeForMfi(liquidHours);
        // Step 3 retrieve marketData ...
        Map.Entry<List<MarketData>, WorkStatus> ret1 = getMarketData(ep.getMarketData(), startTime, stopTime);
        List<MarketData> marketData = ret1.getKey();
        workStatusList.add(ret1.getValue());

        // Step 4 filter marketData by LiquidHours
        List<MarketData> filteredMkData = filter(liquidHoursBySymbol, marketData);

        //group MarketFetcherData times by day
        Map<LocalDate, List<MarketData>> mkDataByDay = groupMarketData(filteredMkData);
        //update cache Data
        mkDataByDay.entrySet().stream().forEach(localDateListEntry -> {
            CacheData.INSTANCE.
                    addMarketData(mfiSeriesName, localDateListEntry.getKey(), localDateListEntry.getValue());
        });

        return workStatusList;
    }

    private Map<LocalDate, List<MarketData>> groupMarketData(List<MarketData> mktDataList) {
        return mktDataList.stream().collect(Collectors.groupingBy(mkt -> mkt.getTimeStamp().toLocalDate()));
    }

    private List<MarketData> filter(Map<String, List<LiquidHours>> liquidHoursBySymbol, List<MarketData> marketData) {
        return marketData.stream().filter(mkData -> {
            String series = mkData.getSeries();
            List<LiquidHours> lqHours = liquidHoursBySymbol.get(series);
            LocalDate ld = mkData.getTimeStamp().toLocalDate();
            LocalTime lt = mkData.getTimeStamp().toLocalTime();
            return lqHours.stream().filter(lq -> isTimeInRange(ld, lt, lq)).findFirst().isPresent();
        }).collect(Collectors.toList());
    }

    private boolean isTimeInRange(LocalDate ld, LocalTime lt, LiquidHours lq) {
        return (lq.getValidFromDateTimeGMT().isBefore(ld) || lq.getValidFromDateTimeGMT().isEqual(ld)) &&
                (lq.getValidToDateTimeGMT().isAfter(ld) || lq.getValidToDateTimeGMT().isEqual(ld)) &&
                (lq.getStartTimeLocal().isBefore(lt) || lq.getStartTimeLocal().equals(lt)) &&
                (lq.getEndTimeLocal().isAfter(lt) || lq.getEndTimeLocal().equals(lt));
    }

    private Map<String, List<LiquidHours>> collectTimeForMfi(List<LiquidHours> liquidHours) {
        Map<String, List<LiquidHours>> ranges = new HashMap<>();
        liquidHours.stream().forEach(l -> ranges.merge(l.getSeriesName(), Collections.singletonList(l),
                (l1, l2) -> Stream.of(l1, l2).flatMap(Collection::stream).collect(Collectors.toList())));
        return ranges;
    }

    private Map.Entry<List<HolidayCalendar>, WorkStatus> getHolidayCalendar(UrlResolver urlResolver) {
        List<HolidayCalendar> holidays = ImmutableList.of();
        WorkStatus workStatus = new WorkStatus("holiday_calendar_for " + mfiSeriesName);
        Response response = null;
        try {
            // TODO have a generalized method to get the bloomberg code from a series name
            String bloombergCode = mfiSeriesName.replace("_", " ");

            response = httpClient.executeGetRequest(urlResolver.getUri().toString(),
                    ImmutableMap.of("symbol", bloombergCode, "symbol_type", BLOOMBERG.toString()));
            if (response.getStatus() == 200) {
                JSONObject jsonResponseObject = (JSONObject) new JSONArray(response.getBody()).get(0);
                holidays = JSONPARSER.getMapper().readValue(jsonResponseObject.getJSONArray("output").toString(),
                        JSONPARSER.getMapper().getTypeFactory().constructCollectionType(List.class, HolidayCalendar.class));
            }
            workStatus.setDuration(response.getDuration()).setStatusCode(response.getStatus());
        } catch (IOException e) {
            e.printStackTrace();
            workStatus.setExceptionMsg(e.getLocalizedMessage());
        }
        return new AbstractMap.SimpleEntry<>(holidays, workStatus);
    }

    private Map.Entry<List<LiquidHours>, WorkStatus> getLiquidHours(UrlResolver urlResolver) {
        List<LiquidHours> liquidHours = ImmutableList.of(); //TODO is it ok to be empty ??
        WorkStatus ws = new WorkStatus();

        Response resp = null;
        try {
            resp = httpClient.executeGetRequest(urlResolver.getUri().toString(), ImmutableMap
                    .of("validToDate", "2100-01-01", "validFromDate", "1900-01-01", "outputFormat", "object", "seriesNames",
                            mfiSeriesName));
            if (resp.getStatus() == 200) {
                JSONObject jobj = new JSONObject(resp.getBody());
                liquidHours = JSONPARSER.getMapper().readValue(jobj.getJSONArray("items").toString(),
                        JSONPARSER.getMapper().getTypeFactory().constructCollectionType(List.class, LiquidHours.class));

            }
        } catch (IOException e) {
            e.printStackTrace();
            ws.setExceptionMsg(e.getLocalizedMessage());
        }

        return new AbstractMap.SimpleEntry<>(liquidHours, ws.
                setWorkName("liquid_hours_for " + mfiSeriesName).
                setDuration(resp.getDuration()).setStatusCode(resp.getStatus()));
    }

    private Map.Entry<List<MarketData>, WorkStatus> getMarketData(UrlResolver urlResolver, String startTime, String stopTime) {
        List<MarketData> marketData = ImmutableList.of();
        WorkStatus ws = new WorkStatus();

        Response resp = null;
        try {
            resp = httpClient.executeGetRequest(urlResolver.getUri().toString(),
                    ImmutableMap.of("seriesName", mfiSeriesName, "startTime", startTime, "stopTime", stopTime));
            if (resp.getStatus() == 200) {
                JSONObject jobj = new JSONObject(resp.getBody());
                marketData = JSONPARSER.getMapper().readValue(jobj.getJSONArray("returnData").toString(),
                        JSONPARSER.getMapper().getTypeFactory().constructCollectionType(List.class, MarketData.class));

            }
        } catch (IOException e) {
            e.printStackTrace();
            ws.setExceptionMsg(e.getLocalizedMessage());
        }

        return new AbstractMap.SimpleEntry<>(marketData, ws.
                setWorkName("marketData_for " + mfiSeriesName).
                setDuration(resp.getDuration()).setStatusCode(resp.getStatus()));
    }
}

