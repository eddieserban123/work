package org.demo;

import org.demo.kafka.consumers.KafkaConsumer;
import org.demo.kafka.producers.KafkaProducer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 */


public class AppSimple01 {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(AppSimple01.class);

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "CountryCounter");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");

        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");


        KafkaProducer producer = KafkaProducer.builder().
                properties(props).
                topic("users").values(Arrays.asList("Ana", "are", "mere")).build();



        KafkaConsumer c1 = KafkaConsumer.builder().topic("users").properties(props).consumerId("id1").build();
        KafkaConsumer c2 = KafkaConsumer.builder().topic("users").properties(props).consumerId("id2").build();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(c1);
        executorService.submit(c2);
        executorService.submit(producer);

        System.in.read();


    }
}
