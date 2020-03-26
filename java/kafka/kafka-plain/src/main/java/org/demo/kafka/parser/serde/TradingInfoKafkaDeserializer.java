package org.demo.kafka.parser.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.demo.kafka.parser.TradingInfoDeserializer;
import org.demo.kafka.trading.TradingInfo;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
@Builder
@Log4j2
public class TradingInfoKafkaDeserializer implements Deserializer<TradingInfo> {

    private ObjectMapper mapper = new ObjectMapper();
    private Class<TradingInfo> deserializedClass;


    public TradingInfoKafkaDeserializer(Class<TradingInfo> deserializedClass) {
        this.deserializedClass = deserializedClass;
        SimpleModule module = new SimpleModule();
        module.addDeserializer(TradingInfo.class, new TradingInfoDeserializer());
        mapper.registerModule(module);
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        if (deserializedClass == null) {
            deserializedClass = (Class<TradingInfo>) configs.get("serializedClass");
        }
    }


    @Override
    public TradingInfo deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        try {
            return mapper.readValue(data, TradingInfo.class);
        } catch (IOException e) {
           log.error(" Error: {}", e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public TradingInfo deserialize(String topic, Headers headers, byte[] data) {
        if (data == null) {
            return null;
        }

        try {
            return mapper.readValue(data, TradingInfo.class);
        } catch (IOException e) {
            log.error(" Error: {}", e.getLocalizedMessage());
        }
        return null;

    }

    @Override
    public void close() {

    }
}
