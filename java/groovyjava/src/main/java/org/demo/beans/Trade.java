package org.demo.beans;

import java.util.Objects;

public class Trade {

    private String aqrid;
    public int price;
    public String tradeDate;


    public Trade(String aqrID,String tradeDate, int val) {
        this.price = val;
        this.aqrid = aqrID;
        this.tradeDate = tradeDate;
    }


    public String getAqrid() {
        return aqrid;
    }

    public Trade(String aqrID, int price, String tradeDate) {
        this.aqrid = aqrID;
        this.price = price;
        this.tradeDate = tradeDate;
    }

    public void setAqrid(String aqrid) {
        this.aqrid = aqrid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return price == trade.price &&
                Objects.equals(aqrid, trade.aqrid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(aqrid, price, tradeDate);
    }
}
