package com.report.handler;

import com.report.entity.classroom.ClassRoom;
import com.report.entity.classroom.ClassRoomKey;
import com.report.service.ClassRoomService;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
@AllArgsConstructor
public class ClassRoomHandler {

    private final ClassRoomService classRoomService;

    private final CassandraTemplate cassandraTemplate;


    public Mono<ServerResponse> all(ServerRequest r) {
        return defaultReadResponse(classRoomService.all());
    }


    public Mono<ServerResponse> getById(ServerRequest r) {
        return defaultReadResponse(classRoomService.get(new ClassRoomKey(id(r), year_month(r))));
    }

    public Mono<ServerResponse> create(ServerRequest r) {
        Mono<ClassRoom> mono = r.bodyToMono(ClassRoom.class)
                .flatMap(c -> classRoomService.create(c.getKey().getId(), c.getKey().getYear_month(), c.getCapacity(),
                        c.getRoomNumber(), c.getDescription()));

        return Mono.from(mono).flatMap(c ->
                {
                    try {

                        String encodedQuery = URLEncoder.encode(c.getKey().getId(), "UTF8");
                        return created(URI.create(String.format("/classRoom/%s", encodedQuery, "UTF8"))).
                                contentType(MediaType.APPLICATION_JSON).build();

                    } catch (UnsupportedEncodingException e) {
                        return Mono.error(e);
                    }
                }
        );
    }

    public Mono<ServerResponse> deleteById(ServerRequest r) {
        return classRoomService.delete(id(r), year_month(r)).flatMap(
                c -> noContent().build()
        );
    }

    public Mono<ServerResponse> updateById(ServerRequest r) {
        Mono<ClassRoom> mono = r.bodyToMono(ClassRoom.class).
                flatMap(c -> classRoomService
                        .update(id(r), year_month(r), c.getCapacity(), c.getRoomNumber(), c.getDescription()));
        //should treat the error case also
        return Mono.from(mono).flatMap(p -> noContent().build()
        );
    }


    private static Mono<ServerResponse> defaultReadResponse(Publisher<ClassRoom> profiles) {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(profiles, ClassRoom.class);
    }

    private static String id(ServerRequest r) {
        return r.pathVariable("id");
    }

    private static String year_month(ServerRequest r) {
        return r.pathVariable("year_month");
    }
}
