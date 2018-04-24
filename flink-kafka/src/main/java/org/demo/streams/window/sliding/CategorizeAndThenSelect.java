package org.demo.streams.window.sliding;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer09;
import org.apache.flink.streaming.util.serialization.JSONDeserializationSchema;
import org.apache.flink.util.Collector;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;

public class CategorizeAndThenSelect {

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
                }).name("name");

        //symbol, price, time

        KeyedStream<ObjectNode, String> futures =
                futuresStream.keyBy(new KeySelector<ObjectNode, String>() {
                    @Override
                    public String getKey(ObjectNode jsonNodes) throws Exception {
                        return jsonNodes.get("name").textValue();
                    }
                });

        SingleOutputStreamOperator<ObjectNode> richStream = futures.map(new MapFunction<ObjectNode, ObjectNode>() {
            @Override
            public ObjectNode map(ObjectNode jsonNodes) throws Exception {

                switch (jsonNodes.get("name").textValue()) {
                    case "AAPL":
                    case "MSFT":
                    case "ADBE":
                        jsonNodes.put("sector", "it");
                        return jsonNodes;
                    case "NKE":
                        jsonNodes.put("sector", "sport");
                        return jsonNodes;
                    default:
                        jsonNodes.put("sector", "unknown");
                        return jsonNodes;
                }
            }
        });

        KeyedStream<ObjectNode, String> rihStreamKeyed = richStream.keyBy(new KeySelector<ObjectNode, String>() {
            @Override
            public String getKey(ObjectNode jsonNodes) throws Exception {
                return jsonNodes.get("sector").textValue();
            }
        });

        rihStreamKeyed.window(TumblingEventTimeWindows.of(Time.seconds(5)))
                .apply(new WindowFunction<ObjectNode, Object, String, TimeWindow>() {
                    @Override
                    public void apply(String s, TimeWindow timeWindow, Iterable<ObjectNode> iterable, Collector<Object> collector) throws Exception {
                        ArrayList<ObjectNode> list = (ArrayList<ObjectNode>) iterable;
                        System.err.println("key:" + s + ", list :" + list);
                    }
                });

        env.execute();
    }

}