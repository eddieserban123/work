package com.example.kafka.consumer;

import com.example.kafka.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.example.kafka.util.Constants.*;

/*
Enable/Disable @Service annotation
Example, that demonstrate how to get the messages for every partition
Each method will have it's own id !
*/

//@Service
public class Consumer02 {

    private final Logger logger = LoggerFactory.getLogger(Consumer02.class);



    @KafkaListener(id = "id0", groupId = "group_id", topicPartitions = {@TopicPartition(topic= TOPIC_NAME, partitions = {"0"})})
    public void clients0(String message) throws IOException {
        logger.info(String.format("#### -> Consumer Id0 Thread Id %s  Consumed message -> %s",Thread.currentThread().getId(), message));
    }

    @KafkaListener(id = "id1", groupId = "group_id", topicPartitions = {@TopicPartition(topic=TOPIC_NAME, partitions = {"1"})})
    public void clients1(String message) throws IOException {
        logger.info(String.format("#### -> Consumer Id1 Thread Id %s  Consumed message -> %s",Thread.currentThread().getId(), message));
    }

    @KafkaListener(id = "id2", groupId = "group_id", topicPartitions = {@TopicPartition(topic=TOPIC_NAME, partitions = {"2"})})
    public void clients2(String message) throws IOException {
        logger.info(String.format("#### -> Consumer Id2 Thread Id %s  Consumed message -> %s",Thread.currentThread().getId(), message));
    }

}