package com.report.configuration;

import com.report.handler.PersonHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
/*
 curl --data '{"id":"1","name":"ana"}' -v -u "user:user" -XPOST  -H "Content-Type:application/json" -H "Accept:application/json" http://localhost:8080/person/
 curl -v -u "user:user" -H "Accept:application/json" -XGET http://localhost:8080/person
 */

@Configuration
public class PersonEndPointConfiguration {
    @Bean
    RouterFunction<ServerResponse> routes(PersonHandler handler) {
        return route(GET("/person"), handler::all)
                .andRoute(GET("/person/{id}"), handler::getById)
                .andRoute(POST("/person"), handler::create)
                .andRoute(PUT("/person/{id}"), handler::updateById)
                .andRoute(DELETE("/person/{id}"), handler::deleteById);

    }
}