package com.example.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

/*
Enable it !
still on the same thread :(
 */
//@Service
public class Consumer01 {

    private final Logger logger = LoggerFactory.getLogger(Consumer01.class);
    private CountDownLatch latch = new CountDownLatch(3);


    @KafkaListener(topics = "${message.topic.name}", groupId = "foo", containerFactory = "fooKafkaListenerContainerFactory")
    public void listenGroupFoo(@Payload String message,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info(String.format("#### -> Consumed message -> %s from partition %s Thread Id %s",
                message, partition, Thread.currentThread().getId()));
        latch.countDown();
    }
}