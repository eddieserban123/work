package org.demo.kafka.producers;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.demo.kafka.trading.TICKER;
import org.demo.kafka.trading.TradingInfo;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Log4j2
@ToString
public class KafkaStockExProducer implements Runnable {


    private String topic;
    private Properties properties;
    TICKER ticker;

    volatile boolean shouldContinue = true;

    @Override
    public void run() {
        try {
            shouldContinue = true;
            org.apache.kafka.clients.producer.KafkaProducer<String, TradingInfo> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(properties);
            Random random = ThreadLocalRandom.current();
            long iter = 0;
            int bound = (1 + random.nextInt(3));
            double price = ticker.getInitValue();
            double increment = price * 0.05;
            while (shouldContinue) {
                ++iter;
                Thread.sleep(1000 * (long) (ticker.getMinIntervalTrading() * random.nextDouble()));
                if (iter % bound == 0) {
                    bound = (1 + random.nextInt(3));
                } else
                    price +=  Math.round((random.nextGaussian() * increment) * 100.0) / 100.0;;

                int volume = 1 + random.nextInt(1_000_000);

                TradingInfo tradingInfo = TradingInfo.builder().ticker(ticker).time(LocalDateTime.now().withNano(0)).value(price).volume(volume).build();
              //  log.info("Trying to send {} ", tradingInfo.toString());
                producer.send(new ProducerRecord<>(topic, ticker.toString(), tradingInfo), (metadata, exception) -> {
                    if (exception != null) {
                        log.error(exception.getMessage());
                    } else {
//                        log.info("Successfully received the details as: \n" +
//                                "Topic:" + metadata.topic() + "\n" +
//                                "Partition: " + metadata.partition() + "\n" +
//                                "Offset: " + metadata.offset() + "\n" +
//                                "Timestamp: " + metadata.timestamp());
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
