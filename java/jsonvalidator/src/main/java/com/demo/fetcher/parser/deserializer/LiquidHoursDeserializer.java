package com.demo.fetcher.parser.deserializer;

import com.demo.fetcher.parser.mfiseriesname.LiquidHours;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LiquidHoursDeserializer extends StdDeserializer<LiquidHours> {

    public LiquidHoursDeserializer() {
        this(null);
    }

    public LiquidHoursDeserializer(Class<?> vc) {
        super(vc);
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm[Z][XXXXX][XXXX]['['VV']']");

    @Override
    public LiquidHours deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        String seriesName = node.get("seriesName").asText();
        LocalTime startTimeLocal = LocalTime.parse(node.get("startTimeLocal").asText());
        LocalTime endTimeLocal = LocalTime.parse(node.get("endTimeLocal").asText());
        String localTimeZoneName = node.get("localTimeZoneName").asText();
        LocalDate validFromDateTimeGMT = LocalDateTime.parse(node.get("validFromDateTimeGMT").asText(), formatter).toLocalDate();
        LocalDate validToDateTimeGMT = LocalDateTime.parse(node.get("validToDateTimeGMT").asText(), formatter).toLocalDate();

        return new LiquidHours()
                .setSeriesName(seriesName)
                .setStartTimeLocal(startTimeLocal)
                .setEndTimeLocal(endTimeLocal)
                .setLocalTimeZoneName(localTimeZoneName)
                .setValidFromDateTimeGMT(validFromDateTimeGMT)
                .setValidToDateTimeGMT(validToDateTimeGMT);
    }
}
