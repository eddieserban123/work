package org.demo.kafka.parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.demo.kafka.trading.TICKER;
import org.demo.kafka.trading.TradingInfo;
import org.demo.kafka.trading.TransactionInfo;

import java.io.IOException;
import java.time.LocalDateTime;

public class TransactionInfoDeserializer extends StdDeserializer<TransactionInfo> {


    public TransactionInfoDeserializer() {
        this(null);
    }

    public TransactionInfoDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public TransactionInfo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setName(jsonNode.get("name").asText());
        transactionInfo.setAmount(jsonNode.get("amount").asInt());
        transactionInfo.setTime(LocalDateTime.parse(jsonNode.get("time").asText()));
        return transactionInfo;
    }

    public static void main(String[] args) {
        LocalDateTime dt = LocalDateTime.now();
        LocalDateTime.parse("2020-03-26T17:52:15.413665");

    }
}
