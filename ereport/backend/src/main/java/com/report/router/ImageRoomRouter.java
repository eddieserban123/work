package com.report.router;

import com.report.handler.ImageRoomHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/*

curl -v -u "user:user" -F  "number=3" -F "year_month=2019-01" -F "file=@/home/eddie/Downloads/kindergardeen/001.jpg" http://localhost:8080/image/room

 */

@Configuration
public class ImageRoomRouter {
    @Bean
    RouterFunction<ServerResponse> roomImagesRoute(ImageRoomHandler handler) {
        return route(GET("/image/room/"), handler::get)
                .andRoute(POST("/image/room/").and(RequestPredicates.accept(MediaType.MULTIPART_FORM_DATA)), handler::create);

    }
}
