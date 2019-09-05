package com.report.handler;

import com.report.entity.imageroom.ImageRoom;
import com.report.entity.imageroom.ImageRoomKey;
import com.report.service.ImageRoomService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@AllArgsConstructor
public class ImageRoomHandler {

    private final ImageRoomService imageRoomService;

    private final CassandraTemplate cassandraTemplate;


    public Mono<ServerResponse> get(ServerRequest r) {

        return imageRoomService.get(new ImageRoomKey(room_number(r), year_month(r))).flatMap(image ->
                {
                   // DataBuffer buffer = new DefaultDataBufferFactory().wrap(image.getContent());
                    return ok()
                            .contentType(MediaType.IMAGE_JPEG)
                            .body(BodyInserters.fromObject(image.getContent()));
                }
        );
    }


    public Mono<ServerResponse> create(ServerRequest r) {

        return r.body(BodyExtractors.toMultipartData()).flatMap(parts ->
        {
            Map<String, Part> map = parts.toSingleValueMap();
            Flux<DataBuffer> room_number = map.get("number").content();
            Flux<DataBuffer> year_month = map.get("year_month").content();
            Mono<DataBuffer> content = DataBufferUtils.join(map.get("file").content());


            return Flux.zip(room_number, year_month, content).flatMap(t ->
                    {
                        try {
//                            ByteBuffer buf = t.getT3().asByteBuffer();
//                            byte[] arr = new byte[buf.remaining()];
//                            buf.get(arr);

                            return imageRoomService.create(new ImageRoomKey(
                                            IOUtils.toString(t.getT1().asInputStream(), StandardCharsets.UTF_8.name()),
                                            IOUtils.toString(t.getT2().asInputStream(), StandardCharsets.UTF_8.name())),
                                    t.getT3().asByteBuffer());
                        } catch (IOException e) {
                            e.printStackTrace();
                            return Flux.error(e);
                        }

                    }

            ).next().flatMap(i -> {
                        try {
                            String room_number_url = URLEncoder.encode(i.getKey().getRoom_number(), "UTF8");
                            String year_month_url = URLEncoder.encode(i.getKey().getYear_month(), "UTF8");
                            return created(URI.create(String.format("/image/room?number=%s&year_month=%s", room_number_url, year_month_url, "UTF8"))).
                                    contentType(MediaType.APPLICATION_JSON).build();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
            );
        });
    }


    private static Mono<ServerResponse> defaultReadResponse(Publisher<ImageRoom> image) {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(image, ImageRoom.class);
    }

    private static String room_number(ServerRequest r) {
        return r.queryParam("number").get();
    }

    private static String year_month(ServerRequest r) {
        return r.queryParam("year_month").get();
    }
}

