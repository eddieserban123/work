package com.example;

import com.example.pojo.User;
import com.example.producer.Producer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.function.Consumer;

/*
https://www.confluent.io/blog/schema-registry-avro-in-spring-boot-application-tutorial/

bin/zookeeper-server-start.sh config/zookeeper.properties
****for one broker:
bin/kafka-server-start.sh config/server.properties
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test
****for threee brokers:
bin/kafka-server-start.sh config/server.properties
bin/kafka-server-start.sh config/server1.properties
bin/kafka-server-start.sh config/server2.properties
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 2 --partitions 3 --topic test

mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"


 */
@SpringBootApplication
public class KafkaSpringApplication {

	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext context = SpringApplication.run(KafkaSpringApplication.class, args);
		Producer producer = context.getBean(Producer.class);
		Consumer consumer = context.getBean(Consumer.class);

		producer.sendMessage(new User("peter", 46));

		System.in.read();
	}

}
