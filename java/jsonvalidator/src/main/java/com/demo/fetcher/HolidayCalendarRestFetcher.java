package com.demo.fetcher;

import com.demo.fetcher.data.Data;
import com.demo.fetcher.data.HolidayCalendarData;
import com.demo.fetcher.data.StringData;
import com.demo.fetcher.params.RestParam;
import com.demo.fetcher.parser.mfiseriesname.HolidayCalendar;
import com.google.common.collect.ImmutableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import static com.demo.fetcher.parser.JsonParser.JSONPARSER;

public class HolidayCalendarRestFetcher extends RestFetcher {

    public HolidayCalendarRestFetcher(RestParam params) {
        super(params);
    }

    @Override
    public Data fetch() {
        StringData data = (StringData) super.fetch();
        JSONObject jobj = (JSONObject) new JSONArray(data.getData()).get(0);

        List<HolidayCalendar> holidays = ImmutableList.of();
        try {
            holidays = JSONPARSER.getMapper().readValue(jobj.getJSONArray("output").toString(),
                    JSONPARSER.getMapper().getTypeFactory().constructCollectionType(List.class, HolidayCalendar.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HolidayCalendarData(holidays);
    }

}
