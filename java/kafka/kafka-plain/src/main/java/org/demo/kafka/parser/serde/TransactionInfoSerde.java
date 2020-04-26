package org.demo.kafka.parser.serde;

import org.apache.kafka.common.serialization.Serdes;
import org.demo.kafka.parser.*;
import org.demo.kafka.trading.TradingInfo;
import org.demo.kafka.trading.TransactionInfo;


public class TransactionInfoSerde extends Serdes.WrapperSerde<TransactionInfo> {
    public TransactionInfoSerde() {
        super(new JsonKafkaSerializer(TransactionInfo.class, new TransactionInfoSerializer()), new JsonKafkaDeserializer(TransactionInfo.class, new TransactionInfoDeserializer()));
    }
}

