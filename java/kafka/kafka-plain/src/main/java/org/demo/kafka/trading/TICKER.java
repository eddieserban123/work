package org.demo.kafka.trading;

public enum TICKER {
    MSFT(130.0, 3),
    ADBE(250.0,5),
    FB(150.0,4),
    BA(100.0,5),
    AAPL(230.0,2),
    SPCE(10.0,10);

    Double initValue;


    Integer minIntervalTrading;

    TICKER(Double initValue, Integer minIntervalTrading) {
        this.initValue = initValue;
        this.minIntervalTrading = minIntervalTrading;
    }


    public Double getInitValue() {
        return initValue;
    }

    public Integer getMinIntervalTrading() {
        return minIntervalTrading;
    }



}
