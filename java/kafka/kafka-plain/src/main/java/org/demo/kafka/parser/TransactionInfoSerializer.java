package org.demo.kafka.parser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.ToString;
import org.demo.kafka.trading.TransactionInfo;

import java.io.IOException;

@ToString
public class TransactionInfoSerializer extends StdSerializer<TransactionInfo> {

    public TransactionInfoSerializer() {
        this(null);
    }

    public TransactionInfoSerializer(Class<TransactionInfo> t) {
        super(t);
    }

    @Override
    public void serialize(
            TransactionInfo value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("name", value.getName());
        jgen.writeStringField("time", value.getTime().toString());
        jgen.writeNumberField("amount", value.getAmount());
        jgen.writeEndObject();
    }
}
