package com.demo.fetcher.data;

import com.demo.fetcher.parser.mfiseriesname.HolidayCalendar;


import java.util.List;

public class HolidayCalendarData implements Data<List<HolidayCalendar>>{
    private List<HolidayCalendar>  holidayCalendar;


    public HolidayCalendarData(List<HolidayCalendar> holidayCalendar) {
        this.holidayCalendar = holidayCalendar;
    }

    @Override
    public List<HolidayCalendar> getData() {
        return holidayCalendar;
    }
}
