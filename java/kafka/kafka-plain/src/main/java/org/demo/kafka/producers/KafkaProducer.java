package org.demo.kafka.producers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.List;
import java.util.Properties;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Log4j2
public class KafkaProducer implements Runnable{

    private String topic;
    private Properties properties;
    private List<String> values;


    @Override
    public void run() {

        org.apache.kafka.clients.producer.KafkaProducer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(properties);
        values.stream().forEach(v->
                producer.send(new ProducerRecord<>(topic, v), (metadata, exception) -> {
                    if(exception!= null) {
                        log.error(exception.getMessage());
                    } else {
//                        log.info("Successfully received the details as: \n" +
//                                "Topic:" + metadata.topic() + "\n" +
//                                "Partition: " + metadata.partition() + "\n" +
//                                "Offset: " + metadata.offset() + "\n" +
//                                "Timestamp: " + metadata.timestamp());
                    }
                })
                );

    }
}
