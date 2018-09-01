package com.demo.folder.tata.fetcher.parser.deserializer;

import com.demo.folder.tata.fetcher.parser.mfiseriesname.TranslateSymbol;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TranslateSymbolDeserializer extends StdDeserializer<TranslateSymbol> {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSSSSS]");

    public TranslateSymbolDeserializer() {
        this(null);
    }

    public TranslateSymbolDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public TranslateSymbol deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        LocalDateTime date = LocalDateTime.parse(node.get("date").asText(), formatter);
        String symbolInput = node.get("symbol_input").asText();

        String symbolGrp = getStringFromNode(node.get("symbol_GRP"));
        String symbolAqrId = getStringFromNode(node.get("symbol_AQRID"));
        String symbol = getStringFromNode(node.get("symbol"));
        String symbolBloomberg = getStringFromNode(node.get("symbol_BLOOMBERG"));

        return new TranslateSymbol()
                .setDate(date)
                .setSymbolInput(symbolInput)
                .setSymbolGrp(symbolGrp)
                .setSymbolAqrId(symbolAqrId)
                .setSymbol(symbol)
                .setSymbolBloomberg(symbolBloomberg);
    }

    private String getStringFromNode(JsonNode node) {
        return node == null ? null : node.asText();
    }
}
