package org.demo.kafka.trading;

import lombok.*;
import lombok.extern.log4j.Log4j2;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Log4j2
public class TradingStatus {

    TICKER ticker;
    int countTrades; // tracking count and sum so we can later calculate avg price
    double sumPrice;
    double minPrice;
    double avgPrice;

    public TradingStatus add(TradingInfo trade) {

        log.info("***********88 {}", trade);

        if (trade.ticker == null)
            throw new IllegalArgumentException("Invalid trade to aggregate: " + trade.toString());

        if (this.ticker == null)
            this.ticker = trade.getTicker();

        if (!this.ticker.equals(trade.ticker))
            throw new IllegalArgumentException("Aggregating stats for trade  ticker " + this.ticker + " but recieved trade  ticker " + trade.ticker);

        if (countTrades == 0) this.minPrice = trade.getValue();

        this.countTrades = this.countTrades + 1;
        this.sumPrice = this.sumPrice + trade.getValue();
        this.minPrice = this.minPrice < trade.getValue() ? this.minPrice : trade.getValue();

        return this;
    }

    public TradingStatus computeAvgPrice() {

        log.info("*******average ");

        this.avgPrice = this.sumPrice / this.countTrades;
        return this;
    }
}
