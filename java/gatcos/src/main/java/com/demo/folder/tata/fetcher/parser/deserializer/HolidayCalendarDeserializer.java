package com.demo.folder.tata.fetcher.parser.deserializer;

import com.demo.folder.tata.fetcher.parser.mfiseriesname.HolidayCalendar;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HolidayCalendarDeserializer extends StdDeserializer<HolidayCalendar> {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSSSSS]");

    public HolidayCalendarDeserializer() {
        this(null);
    }

    public HolidayCalendarDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override public HolidayCalendar deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        LocalDate date = LocalDateTime.parse(node.get("date").asText(), formatter).toLocalDate();
        boolean isHoliday = node.get("IsHoliday").asBoolean();
        boolean isPartialHoliday = node.get("IsPartialHoliday").asBoolean();

        return new HolidayCalendar().setDate(date).setHoliday(isHoliday).setPartialHoliday(isPartialHoliday);
    }

}
