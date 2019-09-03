package com.report.configuration;

import com.report.handler.ClassRoomHandler;
import com.report.handler.PersonHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
/*
persons:
 curl --data '{"id":"1","name":"ana"}' -v -u "user:user" -XPOST  -H "Content-Type:application/json" -H "Accept:application/json" http://localhost:8080/person/
 curl -v -u "user:user" -H "Accept:application/json" -XGET http://localhost:8080/person

classroom:
 curl --data '{"id":"small 3","capacity":15, "roomNumber":"11"}' -v -u "user:user" -XPOST  -H "Content-Type:application/json" -H "Accept:application/json" http://localhost:8080/classroom/
 curl -v -u "user:user" -H "Accept:application/json" -XGET http://localhost:8080/classroom



  docker run --name cas --rm --network testnet -p 9042:9042 -d cassandra:latest
  docker run -it  --rm cassandra cqlsh 172.21.0.1

  echo fs.inotify.max_user_watches=524288 | sudo tee -a /etc/sysctl.conf && sudo sysctl -p


 */

@Configuration
public class PersonEndPointConfiguration {
    @Bean
    RouterFunction<ServerResponse> routes(ClassRoomHandler handler) {
        return route(GET("/classroom"), handler::all)
                .andRoute(GET("/classroom/{id}"), handler::getById)
                .andRoute(POST("/classroom"), handler::create)
                .andRoute(PUT("/classroom/{id}"), handler::updateById)
                .andRoute(DELETE("/classroom/{id}"), handler::deleteById);

    }
}