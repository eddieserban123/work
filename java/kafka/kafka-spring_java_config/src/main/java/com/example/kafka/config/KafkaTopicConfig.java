package com.example.kafka.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/*
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
 */
@Configuration
public class KafkaTopicConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${topic.message.name}")
    private String topicName;


    @Value(value = "${topic.greeting.name}")
    private String greetingName;


    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

//    //do i need this ?
//    @Bean
//    public NewTopic topic1() {
//        return new NewTopic(topicName, 1, (short) 1);
//    }
//
//    //do i need this ?
//    @Bean
//    public NewTopic topicGreetings() {
//        return new NewTopic(greetingName, 3, (short) 2);
//    }
}

