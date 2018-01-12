package org.demo.spark.beans;

import java.io.Serializable;
import java.time.LocalDate;

public class TradeTime implements Serializable {

    private String day;
    private Integer offset;
    private String market_event;
    private Integer aqrid;
    private Long tradetime_millis;

    public TradeTime() {
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getMarket_event() {
        return market_event;
    }

    public void setMarket_event(String market_event) {
        this.market_event = market_event;
    }

    public Integer getAqrid() {
        return aqrid;
    }

    public void setAqrid(Integer aqrid) {
        this.aqrid = aqrid;
    }

    public Long getTradetime_millis() {
        return tradetime_millis;
    }

    public void setTradetime_millis(Long tradetime_millis) {
        this.tradetime_millis = tradetime_millis;
    }

    @Override
    public String toString() {
        return "TradeTime{" +
                "day=" + day +
                ", offset=" + offset +
                ", market_event='" + market_event + '\'' +
                ", aqrid=" + aqrid +
                ", tradetime_millis=" + tradetime_millis +
                '}';
    }
}
