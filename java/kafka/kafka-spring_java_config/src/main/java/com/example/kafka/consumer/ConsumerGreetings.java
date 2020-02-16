package com.example.kafka.consumer;

import com.example.kafka.pojo.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class ConsumerGreetings {


    private final Logger logger = LoggerFactory.getLogger(Consumer01.class);


    @KafkaListener(topics = "${topic.greeting.name}", groupId = "greetings", containerFactory = "greetingsListenerContainerFactory")
    public void listenGroupGreet(@Payload Greeting greeting,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info(String.format("#### -> Consumed message -> %s from partition %s Thread Id %s",
                greeting, partition, Thread.currentThread().getId()));
    }

}
