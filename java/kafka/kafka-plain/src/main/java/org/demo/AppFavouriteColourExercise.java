package org.demo;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.demo.kafka.consumers.KafkaConsumer;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/*
---producer

docker-compose exec broker  kafka-console-producer --broker-list localhost:9092 --topic favourite-color-in  --property parse.key=true  --property key.separator=,

docker-compose exec broker  kafka-console-producer --broker-list localhost:9092 --topic favourite-color-in  --property key.serializer=org.apache.kafka.common.serialization.StringSerializer  --property value.serializer=org.apache.kafka.common.serialization.StringSerializer

--consumer
/opt/tool/confluent/examples/cp-all-in-one$ docker-compose exec broker    kafka-console-consumer --bootstrap-server localhost:29092 --topic favourite-color-out --from-beginning --max-messages 42  --property print.key=true --property print.value=true  --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer       --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer

docker-compose exec broker kafka-streams-application-reset --application-id  favourite-color --input-topics my-input-topic favourite-color --intermediate-topics favourite-color-intermediate
 */

@Log4j2
public class AppFavouriteColourExercise {

    public static final String FAVOURITE_COLOR_TOPIC_IN = "favourite-color-in";
    public static final String FAVOURITE_COLOR_TOPIC_MIDDLE = "favourite-color-middle";
    public static final String FAVOURITE_COLOR_TOPIC_OUT = "favourite-color-out";

    public static void main(String[] args) throws IOException {

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "CountryCounter");
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "favourite-color");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.LongDeserializer");

        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());


        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> source = builder.stream(FAVOURITE_COLOR_TOPIC_IN);


        source.filter((k, v) -> v.contains(",")).
                selectKey((k, v) -> v.split(",")[0]).
                mapValues(v -> v.split(",")[1].toLowerCase()).
                filter((k, v) -> {
                    log.info("****kstream  key  {}  value {}", k, v);
                    switch (v) {
                        case "red":
                        case "green":
                        case "blue":
                            return true;
                        default:
                            return false;
                    }
                }).to(FAVOURITE_COLOR_TOPIC_MIDDLE);


        builder.table(FAVOURITE_COLOR_TOPIC_MIDDLE, Consumed.with(Serdes.String(), Serdes.String())).filter((k, v) -> {
            log.info("****key  {}  value {}", k, v);
            return true;
        }).groupBy((k, v) -> new KeyValue<>(v, v)).count().
                toStream().filter((k, v) -> {
            log.info("****out key  {}  value {}", k, v);
            return true;
        }).to(FAVOURITE_COLOR_TOPIC_OUT, Produced.with(Serdes.String(), Serdes.Long()));


        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.cleanUp();
        ;
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

        org.apache.kafka.clients.producer.KafkaProducer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(props);
//
//        producer.send(new ProducerRecord<>(FAVOURITE_COLOR_TOPIC_IN, null, "eddie1,red"));
//        producer.send(new ProducerRecord<>(FAVOURITE_COLOR_TOPIC_IN, null, "peter2,green"));
//        producer.send(new ProducerRecord<>(FAVOURITE_COLOR_TOPIC_IN, null, "bob3,blue"));
//        producer.send(new ProducerRecord<>(FAVOURITE_COLOR_TOPIC_IN, null, "adi4,blue"));

//        KafkaConsumer c1 = KafkaConsumer.builder().topic(FAVOURITE_COLOR_TOPIC_OUT).properties(props).consumerId("id1").build();
//        KafkaConsumer c2 = KafkaConsumer.builder().topic(FAVOURITE_COLOR_TOPIC_OUT).properties(props).consumerId("id2").build();
//
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        executorService.submit(c1);
//        executorService.submit(c2);



        System.in.read();


    }
}
