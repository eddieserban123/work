package org.demo.streams.window.sliding;

import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer09;
import org.apache.flink.streaming.util.serialization.JSONDeserializationSchema;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class BasicWindowJoinApp
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
        env.enableCheckpointing(2000); // create a checkpoint every 5 seconds
        env.getConfig().setAutoWatermarkInterval(100);

        FlinkKafkaConsumer09<ObjectNode> stream1 = new FlinkKafkaConsumer09(TOPIC1, new JSONDeserializationSchema(), props);
        FlinkKafkaConsumer09<ObjectNode> stream2 = new FlinkKafkaConsumer09(TOPIC2, new JSONDeserializationSchema(), props);

        stream1.setStartFromLatest();
        stream2.setStartFromLatest();

        SingleOutputStreamOperator<ObjectNode> topic1Stream = env.addSource(stream1)
                .assignTimestampsAndWatermarks(new ExtractTimestampAutoGenerateWatermark(Time.milliseconds(100)))
                .name("stream1");


        SingleOutputStreamOperator<ObjectNode> topic2Stream  = env.addSource(stream2)
                .assignTimestampsAndWatermarks(new ExtractTimestampAutoGenerateWatermark(Time.milliseconds(100)))
                .name("stream2");

//        topic1Stream.map(new MapFunction<ObjectNode, Tuple3<String, Integer, String>>() {
//            @Override
//            public Tuple3<String, Integer, String> map(ObjectNode jsonNodes) throws Exception {
//                return new Tuple3(jsonNodes.get("name").asText(), jsonNodes.get("price").asInt(), jsonNodes.get("time"));
//            }
//        }).print();

        topic1Stream.join(topic2Stream).where(new KeySelector<ObjectNode, String>() {
            @Override
            public String getKey(ObjectNode value) throws Exception {
                return value.get("name").asText();
            }
        }).equalTo(new KeySelector<ObjectNode, String>() {
            @Override
            public String getKey(ObjectNode value) throws Exception {
                return value.get("name").asText();
            }
        }).window(TumblingEventTimeWindows.of(Time.milliseconds(1000))).apply(new JoinFunction<ObjectNode, ObjectNode, String>() {
            @Override
            public String join(ObjectNode jsonNodes, ObjectNode jsonNodes2) throws Exception {
                synchronized(TOPIC1) {
                    System.out.println("------------start-------\ntopic1:");
                    System.out.println(jsonNodes);
                    System.out.println("\ntopic2");
                    System.out.println(jsonNodes);
                    System.out.println("\n----------end-----------");
                    return "";
                }
            }
        });
        
//        topic1Stream.coGroup(topic2Stream).where(new KeySelector<ObjectNode, String>() {
//            @Override
//            public String getKey(ObjectNode value) throws Exception {
//                return value.get("name").asText();
//            }
//        }).equalTo(new KeySelector<ObjectNode, String>() {
//            @Override
//            public String getKey(ObjectNode value) throws Exception {
//                return value.get("name").asText();
//            }
//        }).window(TumblingEventTimeWindows.of(Time.milliseconds(1000))).
//                apply(new RichCoGroupFunction<ObjectNode, ObjectNode, String>() {
//                    @Override
//                    public void coGroup(Iterable<ObjectNode> it1, Iterable<ObjectNode> it2,
//                                        Collector<String> collector) throws Exception {
//                        synchronized(TOPIC1) {
//                            System.out.println("------------start-------\ntopic1:");
//                            it1.forEach(System.out::print);
//                            System.out.println("\ntopic2");
//                            it2.forEach(System.out::print);
//                            System.out.println("\n----------end-----------");
//
//                        }
//                    }
//                }).print();

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
            LocalDateTime sharetime = LocalDateTime.parse(jsonNodes.get("time").asText(), formatter);
            ZoneId zoneId = ZoneId.systemDefault(); // or: ZoneId.of("Europe/Oslo");
            long epoch = sharetime.atZone(zoneId).toInstant().toEpochMilli();
            return epoch;
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
