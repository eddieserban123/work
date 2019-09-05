package com.report.service;

import com.report.entity.imageroom.ImageRoom;
import com.report.entity.imageroom.ImageRoomKey;
import com.report.repository.ImageRoomRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;

@Log4j2
@AllArgsConstructor
@Service
public class ImageRoomService {

    private final ImageRoomRepository imageRoomRepository;

    public Mono<ImageRoom> get(ImageRoomKey key) {
        return imageRoomRepository.findById(key);
    }

    public Mono<ImageRoom> create(ImageRoomKey key, ByteBuffer content) {
        return imageRoomRepository.save(new ImageRoom().setKey(key).setContent(content));
    }


}
