package com.report.router;

import com.report.handler.ClassRoomHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/*
curl --data '{"id":"small 3", "year_month":"2018-01","capacity":15, "room_number":"20", "description":"once upon a time"}' -v -u "user:user" -XPOST  -H "Content-Type:application/json" -H "Accept:application/json" http://localhost:8080/classroom/

 curl -v -u "user:user" -H "Accept:application/json" -XGET http://localhost:8080/classroom
 */

@Configuration
public class ClassRoomRouter {
    @Bean
    RouterFunction<ServerResponse> classRoutes(ClassRoomHandler handler) {
        return route(GET("/classroom"), handler::all)
                .andRoute(GET("/classroom/{id}"), handler::getById)
                .andRoute(POST("/classroom"), handler::create)
                .andRoute(PUT("/classroom/{id}"), handler::updateById)
                .andRoute(DELETE("/classroom/{id}"), handler::deleteById);

    }
}