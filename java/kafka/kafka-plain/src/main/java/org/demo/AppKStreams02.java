package org.demo;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

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


public class AppKStreams02 {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(AppKStreams02.class);

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "CountryCounter");
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-application");


        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());



        KafkaProducer producer = KafkaProducer.builder().
                properties(props).
                topic("users").values(Arrays.asList("Ana", "are", "mere")).build();

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> source =
                builder.stream("users");
        source.foreach((s, s2) ->  log.info(" ** {}, {}", s,s2));



        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(producer);

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        System.in.read();


    }
}
