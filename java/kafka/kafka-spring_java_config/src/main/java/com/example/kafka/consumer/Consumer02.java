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


// https://github.com/eugenp/tutorials/tree/master/spring-kafka

// diferrent threads !!! yes
@Service
public class Consumer02 {

    private final Logger logger = LoggerFactory.getLogger(Consumer02.class);
    private CountDownLatch latch = new CountDownLatch(3);

    @KafkaListener(id = "id0", groupId = "foo", topicPartitions = {@TopicPartition(topic= "${message.topic.name}",
            partitions = {"0"})}, containerFactory = "fooKafkaListenerContainerFactory")
    public void listenGroupFoo1(@Payload String message,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info(String.format("#### -> Consumed message -> %s from partition %s Thread Id %s",
                message, partition, Thread.currentThread().getId()));
        latch.countDown();
    }

    @KafkaListener(id = "id1", groupId = "foo", topicPartitions = {@TopicPartition(topic= "${message.topic.name}",
            partitions = {"1"})}, containerFactory = "fooKafkaListenerContainerFactory")
    public void listenGroupFoo2(@Payload String message,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info(String.format("#### -> Consumed message -> %s from partition %s Thread Id %s",
                message, partition, Thread.currentThread().getId()));
        latch.countDown();
    }

    @KafkaListener(id = "id2", groupId = "foo", topicPartitions = {@TopicPartition(topic= "${message.topic.name}",
            partitions = {"2"})}, containerFactory = "fooKafkaListenerContainerFactory")
    public void listenGroupFoo3(@Payload String message,
                                @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info(String.format("#### -> Consumed message -> %s from partition %s Thread Id %s",
                message, partition, Thread.currentThread().getId()));
        latch.countDown();
    }
}
