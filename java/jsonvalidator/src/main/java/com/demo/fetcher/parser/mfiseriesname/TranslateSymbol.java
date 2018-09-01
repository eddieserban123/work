package com.demo.fetcher.parser.mfiseriesname;

import java.time.LocalDateTime;

public class TranslateSymbol {

    private LocalDateTime date;
    private String symbolInput;
    private String symbolGrp;
    private String symbolAqrId;
    private String symbol;
    private String symbolBloomberg;

    public LocalDateTime getDate() {
        return date;
    }

    public TranslateSymbol setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public String getSymbolInput() {
        return symbolInput;
    }

    public TranslateSymbol setSymbolInput(String symbolInput) {
        this.symbolInput = symbolInput;
        return this;
    }

    public String getSymbolGrp() {
        return symbolGrp;
    }

    public TranslateSymbol setSymbolGrp(String symbolGrp) {
        this.symbolGrp = symbolGrp;
        return this;
    }

    public String getSymbolAqrId() {
        return symbolAqrId;
    }

    public TranslateSymbol setSymbolAqrId(String symbolAqrId) {
        this.symbolAqrId = symbolAqrId;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public TranslateSymbol setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public String getSymbolBloomberg() {
        return symbolBloomberg;
    }

    public TranslateSymbol setSymbolBloomberg(String symbolBloomberg) {
        this.symbolBloomberg = symbolBloomberg;
        return this;
    }

    @Override
    public String toString() {
        return "TranslateSymbol{" +
                "date=" + date +
                ", symbolInput='" + symbolInput + '\'' +
                ", symbolGrp='" + symbolGrp + '\'' +
                ", symbolAqrId='" + symbolAqrId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", symbolBloomberg='" + symbolBloomberg + '\'' +
                '}';
    }
}
