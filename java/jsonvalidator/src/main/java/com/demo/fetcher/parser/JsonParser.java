package com.demo.fetcher.parser;

import com.demo.fetcher.parser.deserializer.HolidayCalendarDeserializer;
import com.demo.fetcher.parser.deserializer.LiquidHoursDeserializer;
import com.demo.fetcher.parser.deserializer.MarketDataDeserializer;
import com.demo.fetcher.parser.deserializer.TranslateSymbolDeserializer;
import com.demo.fetcher.parser.mfiseriesname.HolidayCalendar;
import com.demo.fetcher.parser.mfiseriesname.LiquidHours;
import com.demo.fetcher.parser.mfiseriesname.MarketData;
import com.demo.fetcher.parser.mfiseriesname.TranslateSymbol;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;
import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRAP_ROOT_VALUE;

public enum JsonParser {

    JSONPARSER;
    ObjectMapper mapper = new ObjectMapper();

    JsonParser() {

        mapper.enable(READ_UNKNOWN_ENUM_VALUES_AS_NULL);
        mapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(WRAP_ROOT_VALUE);
        mapper.configure(ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        SimpleModule mod = new SimpleModule();





        // Add the custom deserializer to the module
        mod.addDeserializer(MarketData.class, new MarketDataDeserializer());
        mod.addDeserializer(LiquidHours.class, new LiquidHoursDeserializer());
        mod.addDeserializer(TranslateSymbol.class, new TranslateSymbolDeserializer());
        mod.addDeserializer(HolidayCalendar.class, new HolidayCalendarDeserializer());
        //mod.addSerializer(SyncWorker.class, new SyncWorkerSerializer());
        mapper.registerModule(mod);    // Register the module on the mapper

    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}
