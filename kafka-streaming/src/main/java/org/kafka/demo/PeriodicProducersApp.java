package org.kafka.demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.kafka.bean.BuildShare;
import org.kafka.converter.Converter;

import java.util.Properties;

public class PeriodicProducersApp {

    private final static String TOPIC1 = "topic1";
    private final static String TOPIC2 = "topic2";
    private final static String BOOTSTRAP_SERVERS = "localhost:9093,localhost:9094";

    public static void main(String[] args) throws Exception {

        sendMsg(TOPIC1, 800);
        sendMsg(TOPIC2, 900);

    }

    private static Producer<Long, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "ProducerGroup001");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer(props);
    }


    static void sendMsg(final String topic, final int timeInterval) throws InterruptedException {
        final Producer<Long, String> producer = createProducer();
        long time = System.currentTimeMillis();

        new Thread(()->{
            int index = 0;
            try {
            while(true) {

                    String share = Converter.INSTANCE.convert(BuildShare.buildNow());
                     ProducerRecord<Long, String> record =
                            new ProducerRecord(topic, index, share);
                    producer.send(record, (metadata, exception) -> {
                        long elapsedTime = System.currentTimeMillis() - time;
                        if (metadata != null) {
                            System.out.printf("sent record(key=%s value=%s) " +
                                            "meta(partition=%d, offset=%d) time=%d\n",
                                    record.key(),share, metadata.partition(),
                                    metadata.offset(), elapsedTime);
                        } else {
                            exception.printStackTrace();
                        }

                    });
                    Thread.sleep(timeInterval);
                    index += timeInterval;

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            producer.flush();
            producer.close();
        }}).start();


    }
}
