package model;

import java.util.ArrayList;
import java.util.List;

public class Trades {

    private List<Trade> trades = new ArrayList<>();


    public Trades(List<Trade> trades) {
        this.trades = trades;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }

}
