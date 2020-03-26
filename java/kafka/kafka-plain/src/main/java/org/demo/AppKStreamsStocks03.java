package org.demo;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.demo.kafka.parser.TradingSerde;
import org.demo.kafka.parser.serde.TradingInfoKafkaSerializer;
import org.demo.kafka.producers.KafkaStockExProducer;
import org.demo.kafka.trading.TICKER;
import org.demo.kafka.trading.TradingInfo;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 */


public class AppKStreamsStocks03 {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(AppKStreamsStocks03.class);

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-application");


        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", TradingInfoKafkaSerializer.class.getName());


        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(KafkaStockExProducer.builder().properties(props).ticker(TICKER.MSFT).topic("trading").build());

        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, TradingSerde.class.getName());



        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, TradingInfo> source =
                builder.stream("trading");
        source.foreach((s, s2) -> log.info(" ** {}, {}", s, s2));



        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        System.in.read();


    }
}
