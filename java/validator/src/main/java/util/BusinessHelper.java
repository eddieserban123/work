package util;

import model.Trade;
import model.Trades;

import java.util.ArrayList;

public class BusinessHelper {

    public static Trades getTrades(String tradeDate) {
        ArrayList<Trade> trades = new ArrayList<>();
        trades.add(new Trade("1", tradeDate, 34));
        trades.add(new Trade("1", tradeDate, 35));
        trades.add(new Trade("2", tradeDate, 36));
        trades.add(new Trade("2", tradeDate, 36));
        trades.add(new Trade("3", tradeDate, 37));
        trades.add(new Trade("3", tradeDate, 37));
        trades.add(new Trade("3", tradeDate, 38));
        trades.add(new Trade("4", tradeDate, 39));
        trades.add(new Trade("5", tradeDate, 40));

        return new Trades(trades);
    }
}
