package com.demo.fetcher.parser.deserializer;

import com.demo.fetcher.parser.mfiseriesname.MarketData;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MarketDataDeserializer extends StdDeserializer<MarketData> {

    public MarketDataDeserializer() {
        this(null);
    }

    public MarketDataDeserializer(Class<?> vc) {
        super(vc);
    }


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm[Z][XXXXX][XXXX]['['VV']']");

    @Override
    public MarketData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        LocalDateTime timeStamp = LocalDateTime.parse(node.get("timeStamp").asText(), formatter);
        String series = node.get("series").asText();
        String ticker = node.get("ticker").asText();
        BigDecimal cumReturn = new BigDecimal(node.get("cumReturn").asText());
        Double price = node.get("cumReturn").asDouble();
        String priceSource = node.get("priceSource").asText();
        Double volume = node.get("volume").asDouble();
        Double spreadMedian = node.get("spreadMedian").asDouble();
        BigDecimal spreadPercent = new BigDecimal(node.get("spreadPercent").asText());


        return new MarketData().setTimeStamp(timeStamp).setSeries(series).setTicker(ticker).
                setCumReturn(cumReturn).setPrice(price).setPriceSource(priceSource).
                setVolume(volume).setSpreadMedian(spreadMedian).setSpreadPercent(spreadPercent);
    }
}
