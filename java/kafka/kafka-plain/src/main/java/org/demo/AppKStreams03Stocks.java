package org.demo;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.WindowStore;
import org.demo.kafka.parser.JsonKafkaDeserializer;
import org.demo.kafka.parser.TradingInfoDeserializer;
import org.demo.kafka.parser.TradingInfoSerializer;
import org.demo.kafka.parser.serde.TradingInfoSerde;
import org.demo.kafka.parser.JsonKafkaSerializer;
import org.demo.kafka.parser.serde.TradingStatusSerde;
import org.demo.kafka.producers.KafkaStockExProducer;
import org.demo.kafka.trading.TICKER;
import org.demo.kafka.trading.TradingInfo;
import org.demo.kafka.trading.TradingStatus;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 */


public class AppKStreams03Stocks {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(AppKStreams03Stocks.class);

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-application");

//producer
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", TradingInfoKSerializer.class.getName());

        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(KafkaStockExProducer.builder().properties(props).ticker(TICKER.MSFT).topic("trading").build());

//streams
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, TradingInfoSerde.class.getName());


        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, TradingInfo> source =
                builder.stream("trading");
       // source.foreach((s, s2) -> log.info(" ** {}, {}", s, s2));

        KStream<Windowed<String>, TradingStatus > stats  = source.groupByKey().windowedBy(TimeWindows.of(5000).advanceBy(1000))
                .aggregate(TradingStatus::new,(k, v, tradigstatus)-> tradigstatus.add(v),
                        Materialized.<String, TradingStatus, WindowStore<Bytes, byte[]>>as("trading-agregate")
                .withValueSerde(new TradingStatusSerde()))
                .toStream()
                .mapValues((trade) -> trade.computeAvgPrice());

        stats.to("trading-status", /* Produced.keySerde(WindowedSerdes.timeWindowedSerdeFrom(String.class))); */
        Produced.with(WindowedSerdes.timeWindowedSerdeFrom(String.class), new TradingStatusSerde()));

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
        log.info("{}", streams.toString());
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));



        System.in.read();


    }

    static public final class TradingInfoKDeserializer extends JsonKafkaDeserializer {
        public TradingInfoKDeserializer() {
            super(TradingInfo.class, new TradingInfoDeserializer());
        }
    }

    static public final class TradingInfoKSerializer extends JsonKafkaSerializer {
        public TradingInfoKSerializer() {
            super(TradingInfo.class, new TradingInfoSerializer());
        }
    }
}
