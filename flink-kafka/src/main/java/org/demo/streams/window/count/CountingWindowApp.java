package org.demo.streams.window.count;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer09;
import org.apache.flink.streaming.util.serialization.JSONDeserializationSchema;

import javax.annotation.Nullable;
import java.util.Properties;

public class CountingWindowApp {


    private final static String TOPIC1 = "topic1";
    public static void main( String[] args ) throws Exception {




        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
        env.setParallelism(10);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9093");
        props.put("group.id", "group001");


        env.getConfig().disableSysoutLogging();
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(0, 0));
        env.enableCheckpointing(5000); // create a checkpoint every 5 seconds
        env.getConfig().setAutoWatermarkInterval(5000);

        FlinkKafkaConsumer09<ObjectNode> stream = new FlinkKafkaConsumer09(TOPIC1, new JSONDeserializationSchema(), props);


        stream.setStartFromLatest();

        SingleOutputStreamOperator<ObjectNode> topicStream = env.addSource(stream)
                .assignTimestampsAndWatermarks(new ExtractTimestampAutoGenerateWatermark())
                .name("stream1");

        SingleOutputStreamOperator<Tuple3<String, Long, String>> futures =
                topicStream.map(new MapFunction<ObjectNode, Tuple3<String, Long, String >>() {
                    @Override
                    public Tuple3<String, Long, String> map(ObjectNode jsonNodes) throws Exception {
                        return new Tuple3(jsonNodes.get("name").textValue(),jsonNodes.get("price").longValue(),jsonNodes.get("time").textValue());
                    }
                });

        SingleOutputStreamOperator<Tuple3<String, Long, String>> futuresMax =
                futures.keyBy(0).countWindow(10).max(1);

        futuresMax.print();


        env.execute();


    }

    private static class ExtractTimestampAutoGenerateWatermark implements AssignerWithPeriodicWatermarks<ObjectNode> {

        private long difference = Long.MIN_VALUE;
        private long timeStamp;

        @Override
        public Watermark getCurrentWatermark() {

            Watermark watermark;
            if(difference == Long.MIN_VALUE) {
                return new Watermark(difference);
            }
            return new Watermark(System.currentTimeMillis() - difference);
        }

        @Override
        public long extractTimestamp(ObjectNode jsonNodes, long l) {
            timeStamp = jsonNodes.get("time").asLong();
            if(difference == Long.MIN_VALUE) {
                difference = System.currentTimeMillis() - timeStamp;
            }

            return timeStamp;
        }
    }
}
