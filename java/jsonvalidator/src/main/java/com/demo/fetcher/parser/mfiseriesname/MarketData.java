package com.demo.fetcher.parser.mfiseriesname;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MarketData {
    private LocalDateTime timeStamp;
    private String series;
    private String ticker;
    private BigDecimal cumReturn;
    private Double price;
    private String priceSource;
    private Double volume;
    private Double spreadMedian;
    private BigDecimal spreadPercent;


    public MarketData() {
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public MarketData setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public String getSeries() {
        return series;
    }

    public MarketData setSeries(String series) {
        this.series = series;
        return this;
    }

    public String getTicker() {
        return ticker;
    }

    public MarketData setTicker(String ticker) {
        this.ticker = ticker;
        return this;
    }

    public BigDecimal getCumReturn() {
        return cumReturn;
    }

    public MarketData setCumReturn(BigDecimal cumReturn) {
        this.cumReturn = cumReturn;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public MarketData setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getPriceSource() {
        return priceSource;
    }

    public MarketData setPriceSource(String priceSource) {
        this.priceSource = priceSource;
        return this;
    }

    public Double getVolume() {
        return volume;
    }

    public MarketData setVolume(Double volume) {
        this.volume = volume;
        return this;
    }

    public Double getSpreadMedian() {
        return spreadMedian;
    }

    public MarketData setSpreadMedian(Double spreadMedian) {
        this.spreadMedian = spreadMedian;
        return this;
    }

    public BigDecimal getSpreadPercent() {
        return spreadPercent;
    }

    public MarketData setSpreadPercent(BigDecimal spreadPercent) {
        this.spreadPercent = spreadPercent;
        return this;
    }

    @Override
    public String toString() {
        return "\nMarketFetcherData{" +
                "timeStamp=" + timeStamp +
                ", series='" + series + '\'' +
                ", ticker='" + ticker + '\'' +
                ", cumReturn=" + cumReturn +
                ", price=" + price +
                ", priceSource='" + priceSource + '\'' +
                ", volume=" + volume +
                ", spreadMedian=" + spreadMedian +
                ", spreadPercent=" + spreadPercent +
                '}';
    }
}
