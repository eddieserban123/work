package com.example.kafka.producer;

import com.example.kafka.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import static com.example.kafka.util.Constants.TOPIC_NAME;


@Service
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        logger.info(String.format("#### -> Producing message -> %s", message));
        this.kafkaTemplate.send(TOPIC_NAME, message);
    }
}
