package org.demo.kafka.parser.serde;

import org.apache.kafka.common.serialization.Serdes;
import org.demo.kafka.parser.TradingInfoDeserializer;
import org.demo.kafka.parser.JsonKafkaDeserializer;
import org.demo.kafka.parser.JsonKafkaSerializer;
import org.demo.kafka.parser.TradingInfoSerializer;
import org.demo.kafka.trading.TradingInfo;


public class TradingInfoSerde extends Serdes.WrapperSerde<TradingInfo> {
    public TradingInfoSerde() {
        super(new JsonKafkaSerializer(TradingInfo.class, new TradingInfoSerializer()), new JsonKafkaDeserializer(TradingInfo.class, new TradingInfoDeserializer()));
    }
}

