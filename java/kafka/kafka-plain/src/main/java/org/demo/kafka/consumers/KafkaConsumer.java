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
public class KafkaConsumer<K,V> implements Runnable {

    private String consumerId;
    private String topic;
    private Properties properties;


    @Override
    public void run() {
        try (org.apache.kafka.clients.consumer.KafkaConsumer<K, V> consumer =
                     new org.apache.kafka.clients.consumer.KafkaConsumer<>(properties)) {
            consumer.subscribe(Collections.singletonList(topic));
            boolean shouldContinue = true;
            while (shouldContinue) {
                ConsumerRecords<K, V> records = consumer.poll(Duration.ofMillis(100));
                Iterator<ConsumerRecord<K, V>> it = records.iterator();
                while (it.hasNext()) {
                    ConsumerRecord<K, V> record = it.next();
                    log.info("read out {},{} ", record.key(), record.value());
                }

            }
        }

    }
}
