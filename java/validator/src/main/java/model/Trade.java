package model;

import java.util.Objects;

public class Trade implements Comparable<Trade> {
    private String aqrId;
    private String tradeDate;
    private int openPrice;


    public Trade(String aqrId, String tradeDate, int openPrice) {
        this.aqrId = aqrId;
        this.tradeDate = tradeDate;
        this.openPrice = openPrice;
    }

    public String getAqrId() {
        return aqrId;
    }

    public void setAqrId(String aqrId) {
        this.aqrId = aqrId;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public int getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(int openPrice) {
        this.openPrice = openPrice;
    }

    @Override
    public int compareTo(Trade o) {
        return this.openPrice - o.openPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return openPrice == trade.openPrice &&
                Objects.equals(aqrId, trade.aqrId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aqrId, openPrice);
    }

    @Override
    public String toString() {
        return "Trade{" +
                "aqrId='" + aqrId + '\'' +
                ", tradeDate='" + tradeDate + '\'' +
                ", openPrice=" + openPrice +
                '}';
    }
}
