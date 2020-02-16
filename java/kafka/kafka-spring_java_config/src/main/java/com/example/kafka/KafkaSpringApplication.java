package com.example.kafka;

import com.example.kafka.consumer.Consumer01;
import com.example.kafka.consumer.Consumer02;
import com.example.kafka.consumer.ConsumerGreetings;
import com.example.kafka.pojo.Greeting;
import com.example.kafka.producer.Producer;
import com.example.kafka.producer.ProducerGreetings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.function.Consumer;

/*
bin/zookeeper-server-start.sh config/zookeeper.properties
****for one broker:
bin/kafka-server-start.sh config/server.properties
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test
****for threee brokers:
bin/kafka-server-start.sh config/server.properties
bin/kafka-server-start.sh config/server1.properties
bin/kafka-server-start.sh config/server2.properties
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 2 --partitions 3 --topic test

 */
@SpringBootApplication
public class KafkaSpringApplication {

	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext context = SpringApplication.run(KafkaSpringApplication.class, args);
		Producer producer = context.getBean(Producer.class);
		Consumer02 consumer = context.getBean(Consumer02.class);
		System.in.read();
//		producer.sendMessage("ana are mere");
//		producer.sendMessage("ana are si pere");
//		producer.sendMessage("ana are si alune ");
//		producer.sendMessage("ana are si prune ");
//		producer.sendMessage("ana are si mere ");

		System.in.read();
		ProducerGreetings producerGreetings = context.getBean(ProducerGreetings.class);
		producerGreetings.sendGreeting(new Greeting("titi", "hello"));
		producerGreetings.sendGreeting(new Greeting("george", "hello"));
		System.in.read();
		ConsumerGreetings consumerGreet = context.getBean(ConsumerGreetings.class);



		System.in.read();
	}

}
