package com.report.router;

import com.report.handler.ClassRoomHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


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