package com.aqr.tca.repository;

import com.aqr.tca.utils.Topics;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Properties;

@Repository
public class TopicRepository {


    @Value("${kafka.bootstrap.servers}")
    private String BOOTSTRAP_SERVERS;

    public Topics getTopics() {
        final Topics topics = new Topics();
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "getTpics");
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG,2000);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,3000);
        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 4000);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Create the consumer using props.
        try {
            final KafkaConsumer<Long, String> consumer = new KafkaConsumer(props);
            consumer.listTopics().forEach((k,v) -> topics.addTopic(k));
        } catch(KafkaException ex) {
            topics.addTopic("Error with Kafka cluster");
        }

        return topics;
    }


}
