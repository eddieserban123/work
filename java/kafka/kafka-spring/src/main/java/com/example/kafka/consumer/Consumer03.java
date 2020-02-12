package com.example.kafka.consumer;

import com.example.kafka.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.example.kafka.util.Constants.TOPIC_NAME;

/*
Enable/Disable @Service annotation

Example, that demonstrate how to get the messages for a specific partition
there will only ome thread !
Since the initialOffset has been sent to 0 in this listener, all the previously consumed messages from partitions 0
and three will be re-consumed every time this listener is initialized. If setting the offset is not required, we can
use the partitions property of @TopicPartition annotation to set only the partitions without the offset:
*/

@Service
public class Consumer03 {

    private final Logger logger = LoggerFactory.getLogger(Consumer03.class);


    @KafkaListener(
            topicPartitions = @TopicPartition(topic = TOPIC_NAME,
                    partitionOffsets = {
                            @PartitionOffset(partition = "0", initialOffset = "0"),
                            @PartitionOffset(partition = "2", initialOffset = "0")
                    }))
    public void listenToParition(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info(String.format("Received Messasge: %s from partition: %s on thread id %s",
                message, partition, Thread.currentThread().getId()));
    }

}