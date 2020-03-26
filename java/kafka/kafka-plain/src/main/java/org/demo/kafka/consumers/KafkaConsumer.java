package org.demo.kafka.consumers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Log4j2
public class KafkaConsumer implements Runnable {

    private String consumerId;
    private String topic;
    private Properties properties;


    @Override
    public void run() {
        try (org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer =
                     new org.apache.kafka.clients.consumer.KafkaConsumer<>(properties)) {
            consumer.subscribe(Collections.singletonList(topic));
            boolean shouldContinue = true;
            while (shouldContinue) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                Iterator<ConsumerRecord<String, String>> it = records.iterator();
                while (it.hasNext()) {
                    ConsumerRecord<String, String> record = it.next();
                    log.info("{},{} ", record.key(), record.value());
                }

            }
        }

    }
}
