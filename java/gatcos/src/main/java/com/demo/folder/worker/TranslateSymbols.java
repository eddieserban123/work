package com.demo.folder.worker;

import com.demo.folder.tata.cache.CacheData;
import com.demo.folder.tata.config.AppConfig;
import com.demo.folder.tata.config.mfi.MfiSeriesNameEndPoints;
import com.demo.folder.tata.fetcher.httpclient.Client;
import com.demo.folder.tata.fetcher.httpclient.KerberosHttpClient;
import com.demo.folder.tata.fetcher.httpclient.Response;
import com.demo.folder.tata.fetcher.parser.mfiseriesname.TranslateSymbol;
import com.demo.folder.util.AqrUtils;
import com.demo.folder.worker.pojos.WorkStatus;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.demo.folder.tata.fetcher.parser.JsonParser.JSONPARSER;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TranslateSymbols {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranslateSymbols.class);
    protected Client httpClient = new KerberosHttpClient();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public TranslateSymbols() {

    }


    public void start() {

        MfiSeriesNameEndPoints ep = AppConfig.APPCONFIG.getMfiSeriesNameEndPoint();
        LocalDate startDate = LocalDate.parse(AppConfig.APPCONFIG.getStartTime(), formatter);
        LocalDate endDate = LocalDate.parse(AppConfig.APPCONFIG.getStopTime(), formatter);

        List<LocalDate> rangeDates = AqrUtils.getDatesBetween2Dates(startDate, endDate);
        String bloombergCode = String.join(",", AppConfig.APPCONFIG.getMfiSeriesName()).replace('_', ' ');


        rangeDates.stream().forEach(date -> {
            WorkerExecutor.INSTANCE.submit(() -> {
                Map.Entry<List, List<WorkStatus>> res = translateSymbol(ep, bloombergCode, date.toString());

                List<TranslateSymbol> trSymbol = (List<TranslateSymbol>) res.getKey().stream().map(tr ->
                        ((TranslateSymbol) tr).
                                setSymbolBloomberg(((TranslateSymbol) tr).getSymbolInput())).
                        collect(Collectors.toList());

                trSymbol.stream().forEach(tr ->
                        CacheData.INSTANCE.addTranslateSymbol(tr.getSymbolInput().replace(" ", "_"), date, tr));
                return res.getValue();
            });
        });

        IntStream.range(0, rangeDates.size()).forEach(i -> {
            WorkStatus ws = WorkerExecutor.INSTANCE.take().get(0);
            LOGGER.info("**** " + i + " TranslateSymbol finished for  " + ws);

        });

        LOGGER.info("---------");


    }

    private Map.Entry<List, List<WorkStatus>> translateSymbol(MfiSeriesNameEndPoints ep, String mfiSeriesName, String date) {
        Response resp = null;
        WorkStatus workStatus = new WorkStatus();
        List<TranslateSymbol> symbols = null;
        try {
            resp = httpClient.executeGetRequest(
                    ep.getTranslateSymbol().getUri().toString(),
                    ImmutableMap.of("symbol", mfiSeriesName,
                            "from_symbol_type", "BLOOMBERG",
                            "to_symbol_type", "GRP,AQRID",
                            "date", date)
            );

            JSONObject jobj = (JSONObject) new JSONArray(resp.getBody()).get(0);
            symbols = JSONPARSER.getMapper().readValue(jobj.getJSONArray("output").toString(),
                    JSONPARSER.getMapper().getTypeFactory().constructCollectionType(List.class, TranslateSymbol.class));


        } catch (IOException e) {
            e.printStackTrace();
            workStatus.setExceptionMsg(e.getLocalizedMessage());
        }
        return new AbstractMap.SimpleEntry<>(symbols, ImmutableList.of(workStatus.
                setDuration(resp.getDuration()).setStatusCode(resp.getStatus()).
                setWorkName("transalte_symbol " + date)));
    }
}
