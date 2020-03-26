package org.demo.kafka.parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.demo.kafka.trading.TICKER;
import org.demo.kafka.trading.TradingInfo;

import java.io.IOException;
import java.time.LocalDateTime;

public class TradingInfoDeserializer extends StdDeserializer<TradingInfo> {


    public TradingInfoDeserializer() {
        this(null);
    }

    public TradingInfoDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public TradingInfo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        TradingInfo tradingInfo = new TradingInfo();
        tradingInfo.setTicker(TICKER.valueOf(jsonNode.get("ticker").asText()));
        tradingInfo.setValue(jsonNode.get("value").asDouble());
        tradingInfo.setVolume(jsonNode.get("volume").asInt());
        tradingInfo.setTime(LocalDateTime.parse(jsonNode.get("time").asText()));
        return tradingInfo;
    }

    public static void main(String[] args) {
        LocalDateTime dt = LocalDateTime.now();
        LocalDateTime.parse("2020-03-26T17:52:15.413665");

    }
}
