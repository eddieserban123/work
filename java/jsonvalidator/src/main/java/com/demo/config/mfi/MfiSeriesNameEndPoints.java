package com.demo.config.mfi;


import com.demo.config.EndPoints;

public class MfiSeriesNameEndPoints extends EndPoints {
    private UrlResolver translateSymbol;
    private UrlResolver enrichmentData;
    private UrlResolver liquidHours;
    private UrlResolver holidayCalendar;

    public MfiSeriesNameEndPoints(UrlResolver marketData, UrlResolver translateSymbol, UrlResolver enrichmentData,
            UrlResolver holidayCalendar) {
        super(marketData);
        this.translateSymbol = translateSymbol;
        this.enrichmentData = enrichmentData;
        this.holidayCalendar = holidayCalendar;
    }

    public MfiSeriesNameEndPoints() {
        super(null);
    }

    public UrlResolver getTranslateSymbol() {
        return translateSymbol;
    }

    public MfiSeriesNameEndPoints setTranslateSymbol(UrlResolver translateSymbol) {
        this.translateSymbol = translateSymbol;
        return this;
    }

    public UrlResolver getEnrichmentData() {
        return enrichmentData;
    }

    public MfiSeriesNameEndPoints setEnrichmentData(UrlResolver enrichmentData) {
        this.enrichmentData = enrichmentData;
        return this;
    }

    public UrlResolver getLiquidHours() {
        return liquidHours;
    }

    public MfiSeriesNameEndPoints setLiquidHours(UrlResolver liquidHours) {
        this.liquidHours = liquidHours;
        return this;
    }

    public UrlResolver getHolidayCalendar() {
        return holidayCalendar;
    }

    public MfiSeriesNameEndPoints setHolidayCalendar(UrlResolver holidayCalendar) {
        this.holidayCalendar = holidayCalendar;
        return this;
    }
}
