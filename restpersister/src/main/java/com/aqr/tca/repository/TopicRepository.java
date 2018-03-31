package com.aqr.tca.repository;

import com.aqr.tca.utils.Topics;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Repository;

import java.util.Properties;

@Repository
public class TopicRepository {


    public Topics getTopics() {
        final Topics topics = new Topics();
        String BOOTSTRAP_SERVERS = "localhost:9092";
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaExampleConsumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Create the consumer using props.
        final KafkaConsumer<Long, String> consumer = new KafkaConsumer(props);
        consumer.listTopics().forEach((k,v) -> topics.addTopic(k));
        return topics;
    }


}
