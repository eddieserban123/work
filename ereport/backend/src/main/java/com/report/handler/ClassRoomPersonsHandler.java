package com.report.handler;

import com.report.entity.classroom.ClassRoom;
import com.report.entity.classroompersons.ClassRoomPersons;
import com.report.service.ClassRoomPersonsService;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

import static com.report.util.AppDateFormatter.getDateFormatter;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
@AllArgsConstructor
public class ClassRoomPersonsHandler {

    private final ClassRoomPersonsService classRoomPersonsService;
    private final CassandraTemplate cassandraTemplate;


    public Mono<ServerResponse> create(ServerRequest r) {
        return r.bodyToMono(ClassRoomPersons.class).flatMap(c -> {
            try {
                classRoomPersonsService.insertPeronsInClassRoom(c.getKey().getId_classroom(), c.getKey().getSnapshot_date(), c.getPersonId());
                String classRoomIdEncoded = URLEncoder.encode(c.getKey().getId_classroom(), "UTF8");
                String dateEncoded = URLEncoder.encode(c.getKey().getSnapshot_date().format(getDateFormatter()), "UTF8");

                return created(URI.create(String.format("/classRoom/%s/%s", classRoomIdEncoded,
                        dateEncoded))).
                        contentType(MediaType.APPLICATION_JSON).build();
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
                return status(HttpStatus.EXPECTATION_FAILED).build();
            }
        });


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
