package com.demo.folder.tata.fetcher.parser.mfiseriesname;

import java.util.List;

public class MfiSeriesName {
    private TranslateSymbol trSymbol;
    private List<MarketData> mktData;

    public MfiSeriesName() {
    }

    public MfiSeriesName(TranslateSymbol trSymbol, List<MarketData> mktData) {
        this.trSymbol = trSymbol;
        this.mktData = mktData;
    }

    public TranslateSymbol getTrSymbol() {
        return trSymbol;
    }

    public void setTrSymbol(TranslateSymbol trSymbol) {
        this.trSymbol = trSymbol;
    }

    public List<MarketData> getMktData() {
        return mktData;
    }

    public void setMktData(List<MarketData> mktData) {
        this.mktData = mktData;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\nMfiSeriesName{" +
                "trSymbol=" + trSymbol +
                ", mktData=").append(mktData
        );
        return sb.toString();


    }
}
