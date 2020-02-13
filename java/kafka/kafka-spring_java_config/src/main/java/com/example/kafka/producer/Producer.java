package com.example.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


@Service
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @Value(value = "${message.topic.name}")
    private String topicName;


    public void sendMessage(String message) {
        logger.info(String.format("#### -> Producing message -> %s", message));

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info(String.format("Sent message=[ %s] with offset=[ %s ]", message, result.getRecordMetadata().offset()));
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.info(String.format("Unable to send message=[%s] due to : ", ex.getMessage()));
            }
        });
    }
}
