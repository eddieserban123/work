package org.demo.kafka.parser.serde;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.serialization.Serializer;
import org.demo.kafka.parser.TradingInfoSerializer;
import org.demo.kafka.trading.TradingInfo;

import java.util.Map;


@AllArgsConstructor
@Builder
@Log4j2
public class TradingInfoKafkaSerializer implements Serializer<TradingInfo> {

    private ObjectMapper mapper = new ObjectMapper();

    public TradingInfoKafkaSerializer() {

        SimpleModule module = new SimpleModule();
        module.addSerializer(TradingInfo.class, new TradingInfoSerializer());
        mapper.registerModule(module);
    }


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, TradingInfo data) {
        try {
            byte json[] = mapper.writeValueAsBytes(data);
            log.info("Produce {}", new String(json));
            return json;
        } catch (JsonProcessingException e) {
            log.error("Unable to serialize object {}", data, e);
            return null;
        }
    }

    @Override
    public void close() {
    }
}