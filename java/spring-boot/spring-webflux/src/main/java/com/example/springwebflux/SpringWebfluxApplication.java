package com.example.springwebflux;

import com.example.springwebflux.handler.OfficerHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;


@SpringBootApplication
//@EnableWebFlux
public class SpringWebfluxApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringWebfluxApplication.class, args);

    }

    @Bean
    public RouterFunction<ServerResponse> routerFunctionA(OfficerHandler handler) {
        return RouterFunctions.route(
                GET("/route/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::getOfficer).
                andRoute(GET("/route").and(accept(MediaType.APPLICATION_JSON)),handler::listOfficer).
                andRoute(POST("/route").and(accept(MediaType.APPLICATION_JSON)),handler::createOfficer);
    }


}

