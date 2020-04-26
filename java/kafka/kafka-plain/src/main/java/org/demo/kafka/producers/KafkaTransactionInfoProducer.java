package org.demo.kafka.producers;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.demo.kafka.trading.TICKER;
import org.demo.kafka.trading.TradingInfo;
import org.demo.kafka.trading.TransactionInfo;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Log4j2
@ToString
public class KafkaTransactionInfoProducer implements Runnable {



    private String topic;
    private Properties properties;
    volatile boolean shouldContinue = true;

    @Override
    public void run() {
        try {
            List<String> names = Arrays.asList("Bob", "Peter", "Marry", "Anne", "Eddie", "Titi");
            shouldContinue = true;
            org.apache.kafka.clients.producer.KafkaProducer<String, TradingInfo> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(properties);
            Random random = ThreadLocalRandom.current();
            while (shouldContinue) {
                Thread.sleep(1000);
                int volume = 1 + random.nextInt(1_000_000);

                TransactionInfo transactionInfo = TransactionInfo.builder().time(LocalDateTime.now().withNano(0)).amount(volume).name(names.get(random.nextInt(6))).build();
                producer.send(new ProducerRecord(topic,  transactionInfo), (metadata, exception) -> {
                log.info(" sent *** {}", transactionInfo);
                    if (exception != null) {
                        log.error(exception.getMessage());
                    } else {
                        log.info("Successfully received the details as: \n" +
                                "Topic:" + metadata.topic() + "\n" +
                                "Partition: " + metadata.partition() + "\n" +
                                "Offset: " + metadata.offset() + "\n" +
                                "Timestamp: " + metadata.timestamp());
                    }
                });


            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void shutDown() {
        shouldContinue = false;
    }

}
