package org.demo.kafka.parser.serde;

import org.apache.kafka.common.serialization.Serdes;
import org.demo.kafka.parser.JsonKafkaDeserializer;
import org.demo.kafka.parser.JsonKafkaSerializer;
import org.demo.kafka.trading.TradingStatus;


public class TradingStatusSerde extends Serdes.WrapperSerde<TradingStatus> {
    public TradingStatusSerde() {
        super(new JsonKafkaSerializer(TradingStatus.class), new JsonKafkaDeserializer(TradingStatus.class));
    }
}

