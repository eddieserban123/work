package com.report.configuration;

import com.report.handler.PersonHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class PersonEndPointConfiguration {
    @Bean
    RouterFunction<ServerResponse> routes(PersonHandler handler) {
        return route(GET("/person"), handler::all)
                .andRoute(GET("/person/{id}"), handler::getById)
                .andRoute(POST("/person"), handler::create)
                .andRoute(PUT("/person/{id}"), handler::updateById);

    }
}