package com.example.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.example.kafka.util.Constants.TOPIC_NAME;


/*
Enable/Disable @Service annotation
Simple example, that demonstrate how to get the message, partition id and
also is worth mentioning thst for different partitions there will be the same thread :(
 */
//@Service
public class Consumer01 {

    private final Logger logger = LoggerFactory.getLogger(Consumer01.class);

    // same thread Id :(

    @KafkaListener(topics = TOPIC_NAME, groupId = "group_id")
    public void consume(@Payload String message,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s from partition %s Thread Id %s",
                message, partition, Thread.currentThread().getId()));
    }
}