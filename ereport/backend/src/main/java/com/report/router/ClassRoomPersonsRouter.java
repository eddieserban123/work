package com.report.router;

import com.report.handler.ClassRoomPersonsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/*
curl --data '{"id_classroom":"small 3", "snapshot_date":"2019/01/01","person_id":"15"}' -v -u "user:user" -XPOST  -H "Content-Type:application/json" -H "Accept:application/json" http://localhost:8080/classroompersons/

 */

@Configuration
public class ClassRoomPersonsRouter {
    @Bean
    RouterFunction<ServerResponse> classPersonsRoutes(ClassRoomPersonsHandler handler) {
        return route(POST("/classroompersons"), handler::create);

    }
}