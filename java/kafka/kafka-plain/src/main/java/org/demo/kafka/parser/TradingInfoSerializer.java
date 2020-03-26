package org.demo.kafka.parser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.ToString;
import org.demo.kafka.trading.TradingInfo;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@ToString
public class TradingInfoSerializer extends StdSerializer<TradingInfo> {

    public TradingInfoSerializer() {
            this(null);
        }

    public TradingInfoSerializer(Class<TradingInfo> t) {
            super(t);
        }

        @Override
        public void serialize(
                TradingInfo value, JsonGenerator jgen, SerializerProvider provider)
      throws IOException, JsonProcessingException {
            jgen.writeStartObject();
            jgen.writeStringField("ticker", value.getTicker().toString());
            jgen.writeStringField("time", value.getTime().toString());
            jgen.writeNumberField("value", value.getValue());
            jgen.writeNumberField("volume", value.getVolume());
            jgen.writeEndObject();
        }
    }
