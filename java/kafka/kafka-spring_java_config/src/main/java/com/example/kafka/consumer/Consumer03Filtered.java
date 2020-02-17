package com.example.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;



// diferrent threads !!! yes
@Service
public class Consumer03Filtered {

    private final Logger logger = LoggerFactory.getLogger(Consumer03Filtered.class);
    private CountDownLatch latch = new CountDownLatch(3);

    @KafkaListener(id = "id0", groupId = "filter", topicPartitions = {@TopicPartition(topic= "${topic.message.name}",
            partitions = {"0"})}, containerFactory = "filterKafkaListenerContainerFactory")
    public void listenGroupFoo1(@Payload String message,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info(String.format("#### -> Filter Consumed message -> %s from partition %s Thread Id %s",
                message, partition, Thread.currentThread().getId()));
        latch.countDown();
    }

    @KafkaListener(id = "id1", groupId = "filter", topicPartitions = {@TopicPartition(topic= "${topic.message.name}",
            partitions = {"1"})}, containerFactory = "filterKafkaListenerContainerFactory")
    public void listenGroupFoo2(@Payload String message,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info(String.format("#### -> Filter Consumed message -> %s from partition %s Thread Id %s",
                message, partition, Thread.currentThread().getId()));
        latch.countDown();
    }

    @KafkaListener(id = "id2", groupId = "filter", topicPartitions = {@TopicPartition(topic= "${topic.message.name}",
            partitions = {"2"})}, containerFactory = "filterKafkaListenerContainerFactory")
    public void listenGroupFoo3(@Payload String message,
                                @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info(String.format("#### -> Filter Consumed message -> %s from partition %s Thread Id %s",
                message, partition, Thread.currentThread().getId()));
        latch.countDown();
    }
}
