package com.demo.folder.tata.fetcher.parser.mfiseriesname;

import java.time.LocalDate;
import java.time.LocalTime;

public class LiquidHours {

    private String seriesName;
    private LocalTime startTimeLocal;
    private LocalTime endTimeLocal;
    private String localTimeZoneName;
    private LocalDate validFromDateTimeGMT;
    private LocalDate validToDateTimeGMT;

    public String getSeriesName() {
        return seriesName;
    }

    public LiquidHours setSeriesName(String seriesName) {
        this.seriesName = seriesName;
        return this;
    }

    public LocalTime getStartTimeLocal() {
        return startTimeLocal;
    }

    public LiquidHours setStartTimeLocal(LocalTime startTimeLocal) {
        this.startTimeLocal = startTimeLocal;
        return this;
    }

    public LocalTime getEndTimeLocal() {
        return endTimeLocal;
    }

    public LiquidHours setEndTimeLocal(LocalTime endTimeLocal) {
        this.endTimeLocal = endTimeLocal;
        return this;
    }

    public String getLocalTimeZoneName() {
        return localTimeZoneName;
    }

    public LiquidHours setLocalTimeZoneName(String localTimeZoneName) {
        this.localTimeZoneName = localTimeZoneName;
        return this;
    }

    public LocalDate getValidFromDateTimeGMT() {
        return validFromDateTimeGMT;
    }

    public LiquidHours setValidFromDateTimeGMT(LocalDate validFromDateTimeGMT) {
        this.validFromDateTimeGMT = validFromDateTimeGMT;
        return this;
    }

    public LocalDate getValidToDateTimeGMT() {
        return validToDateTimeGMT;
    }

    public LiquidHours setValidToDateTimeGMT(LocalDate validToDateTimeGMT) {
        this.validToDateTimeGMT = validToDateTimeGMT;
        return this;
    }
}
