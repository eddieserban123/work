package org.demo.kafka.parser;

import org.apache.kafka.common.serialization.Serdes;
import org.demo.kafka.parser.serde.TradingInfoKafkaDeserializer;
import org.demo.kafka.parser.serde.TradingInfoKafkaSerializer;
import org.demo.kafka.trading.TradingInfo;


public class TradingSerde extends Serdes.WrapperSerde<TradingInfo> {
    public TradingSerde() {
        super(new TradingInfoKafkaSerializer(), new TradingInfoKafkaDeserializer(TradingInfo.class));
    }
}

