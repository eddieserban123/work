package org.demo.streams;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer09;
import org.apache.flink.streaming.util.serialization.JSONDeserializationSchema;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import scala.Tuple3;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App 
{

    private final static String TOPIC1 = "topic1";
    private final static String TOPIC2 = "topic2";
    public static void main( String[] args ) throws Exception {






        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
        env.setParallelism(10);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        Properties props = new Properties();
        props.put("bootstrap.servers","localhost:9093");
        props.put("group.id","group001");



        env.getConfig().disableSysoutLogging();
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(0, 0));
        env.enableCheckpointing(30000); // create a checkpoint every 5 seconds
        env.getConfig().setAutoWatermarkInterval(5000);

        FlinkKafkaConsumer09<ObjectNode> stream1 = new FlinkKafkaConsumer09(TOPIC1, new JSONDeserializationSchema(), props);
        FlinkKafkaConsumer09<ObjectNode> stream2 = new FlinkKafkaConsumer09(TOPIC2, new JSONDeserializationSchema(), props);

        stream1.setStartFromLatest();
        stream2.setStartFromLatest();

        SingleOutputStreamOperator<ObjectNode> topic1Stream = env.addSource(stream1)
                .assignTimestampsAndWatermarks(new ExtractTimestampAutoGenerateWatermark(Time.seconds(20)))
                .name("stream1");


        SingleOutputStreamOperator<ObjectNode> topic2Stream  = env.addSource(stream2)
                .assignTimestampsAndWatermarks(new ExtractTimestampAutoGenerateWatermark(Time.seconds(20)))
                .name("stream2");

        topic1Stream.map(new MapFunction<ObjectNode, Tuple3<String, Integer, String>>() {
            @Override
            public Tuple3<String, Integer, String> map(ObjectNode jsonNodes) throws Exception {
                return new Tuple3(jsonNodes.get("name").asText(), jsonNodes.get("price").asInt(), jsonNodes.get("time"));
            }
        }).print();

        env.execute();






    }
    private static class ExtractTimestampAutoGenerateWatermark extends  BoundedOutOfOrdernessTimestampExtractor<ObjectNode> {




        public ExtractTimestampAutoGenerateWatermark(Time maxOutOfOrderness) {
            super(maxOutOfOrderness);
        }

        @Nullable
        @Override
        public long extractTimestamp(ObjectNode jsonNodes) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            String timeText = jsonNodes.get("time").asText();
            LocalDateTime sharetime = LocalDateTime.parse(jsonNodes.get("time").asText(), formatter);
            LocalDateTime startOfDay = LocalDate.parse(timeText, formatter).atStartOfDay();
            long millis = ChronoUnit.MILLIS.between(sharetime, startOfDay);
            return millis;
        }

    }

//    private static class ExtractTimestampAutoGenerateWatermark implements AssignerWithPeriodicWatermarks<ObjectNode> {
//
//        private long timeStamp;
//
//        private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss.SSS");
//        @Nullable
//        @Override
//        public Watermark getCurrentWatermark() {
//           return new Watermark(System.currentTimeMillis() - )
//        }
//        @Nullable
//        @Override
//        public long extractTimestamp(ObjectNode jsonNodes, long l) {
//            String timeText = jsonNodes.get("time").asText();
//            LocalDateTime sharetime = LocalDateTime.parse(jsonNodes.get("time").asText());
//            LocalDateTime startOfDay = LocalDate.parse(timeText, formatter).atStartOfDay();
//            long millis = ChronoUnit.MILLIS.between(sharetime, startOfDay);
//            timeStamp = millis;
//            return millis;
//        }
//    }
}
