package com.example.kafka.producer;

import com.example.kafka.pojo.Greeting;
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
public class ProducerGreetings {

    private static final Logger logger = LoggerFactory.getLogger(ProducerGreetings.class);


    @Autowired
    private KafkaTemplate<String, Greeting> kafkaTemplate;


    @Value(value = "${topic.greeting.name}")
    private String topicName;


    public void sendGreeting(Greeting greeting) {
        logger.info(String.format("#### -> Producing greeting -> %s", greeting));

        ListenableFuture<SendResult<String, Greeting>> future = kafkaTemplate.send(topicName, greeting);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, Greeting> result) {
                logger.info(String.format("Sent message=[ %s] with offset=[ %s ]", greeting, result.getRecordMetadata().offset()));
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.info(String.format("Unable to send message=[%s] due to : ", ex.getMessage()));
            }
        });
    }
}
