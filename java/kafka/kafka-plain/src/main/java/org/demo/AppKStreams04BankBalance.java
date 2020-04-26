package org.demo;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.demo.kafka.parser.*;
import org.demo.kafka.parser.serde.TradingStatusSerde;
import org.demo.kafka.parser.serde.TransactionInfoSerde;
import org.demo.kafka.producers.KafkaTransactionInfoProducer;
import org.demo.kafka.trading.TransactionInfo;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 */


public class AppKStreams04BankBalance {

    public static final String TOPIC_IN = "bank_balance_in";

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(AppKStreams04BankBalance.class);

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-application");

// for producers

        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", TransactionInfoKSerializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

// starting a producer for transactions !
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(KafkaTransactionInfoProducer.builder().properties(props).topic(TOPIC_IN).build());
// for streams
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, TransactionInfoSerde.class.getName());


        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, TransactionInfo> source =
                builder.stream(TOPIC_IN);
        // source.foreach((s, s2) -> log.info(" ** {}, {}", s, s2));

        KTable<String, Integer> ktSum = source.groupBy((key, value) -> value.getName()).
                aggregate(
                        () -> 0,
                        (aggKey, newValue, aggValue) -> aggValue + newValue.getAmount(),
                        Materialized.<String, Integer, KeyValueStore<Bytes, byte[]>>as("aggregated-stream-sum") /* state store name */
                                .withValueSerde(Serdes.Integer())); /* serde for aggregate value */

        ktSum.toStream().peek((k,v) -> log.info("*****************************************{} --->  sum {}",k, v));

        KTable<String, Integer> ktMax = source.groupBy((key, value) -> value.getName()).
                aggregate(
                        () -> 0,
                        (aggKey, newValue, aggValue) -> newValue.getAmount()> aggValue ? newValue.getAmount():aggValue,
                        Materialized.<String, Integer, KeyValueStore<Bytes, byte[]>>as("aggregated-stream-max") /* state store name */
                                .withValueSerde(Serdes.Integer())); /* serde for aggregate value */


        ktMax.toStream().peek((k,v) -> log.info("*****************************************{} --->  max {}",k, v));


        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();


//        KStream<Windowed<String>, TradingStatus > stats  = source.groupByKey().windowedBy(TimeWindows.of(5000).advanceBy(1000))
//                .aggregate(TradingStatus::new,(k, v, tradigstatus)-> tradigstatus.add(v),
//                        Materialized.<String, TradingStatus, WindowStore<Bytes, byte[]>>as("trading-agregate")
//                .withValueSerde(new TradingStatusSerde()))
//                .toStream()
//                .mapValues((trade) -> trade.computeAvgPrice());

//        stats.to("trading-status", /* Produced.keySerde(WindowedSerdes.timeWindowedSerdeFrom(String.class))); */
//        Produced.with(WindowedSerdes.timeWindowedSerdeFrom(String.class), new TradingStatusSerde()));

//        KafkaStreams streams = new KafkaStreams(builder.build(), props);
//        streams.start();
//        log.info("{}", streams.toString());
//        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));


        System.in.read();


    }

    static public final class TransactionInfoKDeserializer extends JsonKafkaDeserializer {
        public TransactionInfoKDeserializer() {
            super(TransactionInfo.class, new TransactionInfoDeserializer());
        }
    }

    static public final class TransactionInfoKSerializer extends JsonKafkaSerializer {
        public TransactionInfoKSerializer() {
            super(TransactionInfo.class, new TransactionInfoSerializer());
        }
    }
}
