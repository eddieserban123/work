package org.demo.streams.window.sliding;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer09;
import org.apache.flink.streaming.util.serialization.JSONDeserializationSchema;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class WindowSlidingMax {

    private final static String TOPIC1 = "topic1";

    public static void main(String[] args) throws Exception {


        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(10);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9093");
        props.put("group.id", "group001");


        env.getConfig().disableSysoutLogging();
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(0, 0));
        env.enableCheckpointing(5000); // create a checkpoint every 5 seconds


        FlinkKafkaConsumer09<ObjectNode> stream = new FlinkKafkaConsumer09(TOPIC1, new JSONDeserializationSchema(), props);


        SingleOutputStreamOperator<ObjectNode> futuresStream =
                env.addSource(stream).assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<ObjectNode>(Time.milliseconds(0)) {
                    @Override
                    public long extractTimestamp(ObjectNode jsonNodes) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                        LocalDateTime sharetime = LocalDateTime.parse(jsonNodes.get("time").asText(), formatter);
                        ZoneId zoneId = ZoneId.systemDefault(); // or: ZoneId.of("Europe/Oslo");
                        long epoch = sharetime.atZone(zoneId).toInstant().toEpochMilli();
                        return epoch;
                    }
                }).name("mystream");

        //symol, price, time

        SingleOutputStreamOperator<Tuple3<String, Long, String>> futures =
                futuresStream.map(new MapFunction<ObjectNode, Tuple3<String, Long, String>>() {
                    @Override
                    public Tuple3<String, Long, String> map(ObjectNode jsonNodes) throws Exception {
                        return new Tuple3(jsonNodes.get("name").textValue(), jsonNodes.get("price").longValue(), jsonNodes.get("time").textValue());
                    }
                });
        KeyedStream<Tuple3<String, Long, String>, Tuple> tuple3TupleKeyedStream = futures.keyBy(0);
        WindowedStream<Tuple3<String, Long, String>, Tuple, TimeWindow> windowFutures =
                tuple3TupleKeyedStream.window(TumblingEventTimeWindows.of(Time.seconds(5),Time.seconds(1)));

        SingleOutputStreamOperator<Tuple3<String, Long, String>> maxStream = windowFutures.maxBy(1);

        maxStream.print();
        env.execute();


    }
}
