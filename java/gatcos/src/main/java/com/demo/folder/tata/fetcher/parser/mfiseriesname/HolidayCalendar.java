package com.demo.folder.tata.fetcher.parser.mfiseriesname;

import java.time.LocalDate;

public class HolidayCalendar {
    private LocalDate date;
    private boolean isHoliday;
    private boolean isPartialHoliday;

    public HolidayCalendar() {
    }

    public LocalDate getDate() {
        return date;
    }

    public HolidayCalendar setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public boolean isHoliday() {
        return isHoliday;
    }

    public HolidayCalendar setHoliday(boolean isHoliday) {
        this.isHoliday = isHoliday;
        return this;
    }

    public boolean isPartialHoliday() {
        return isPartialHoliday;
    }

    public HolidayCalendar setPartialHoliday(boolean isPartialHoliday) {
        this.isPartialHoliday = isPartialHoliday;
        return this;
    }

    @Override
    public String toString() {
        return "HolidayCalendar{" +
                "date=" + date +
                ", isHoliday=" + isHoliday +
                ", isPartialHoliday=" + isPartialHoliday +
                '}';
    }
}
