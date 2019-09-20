package com.report.router;

import com.report.handler.ClassRoomHandler;
import com.report.handler.ClassRoomKidsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/*
curl --data '{"id_classroom":"small 3", "snapshot_date":"2019/01/01","person_id":"15"}' -v -u "user:user" -XPOST  -H "Content-Type:application/json" -H "Accept:application/json" http://localhost:8080/classroomkids/

 curl -v -u "user:user" -H "Accept:application/json" -XGET http://localhost:8080/classroom
 */

@Configuration
public class ClassRoomKidsRouter {
    @Bean
    RouterFunction<ServerResponse> classKidsRoutes(ClassRoomKidsHandler handler) {
        return route(POST("/classroomkids"), handler::create);

    }
}