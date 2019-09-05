package com.report.repository;

import com.report.entity.imageroom.ImageRoom;
import com.report.entity.imageroom.ImageRoomKey;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ImageRoomRepository extends ReactiveCassandraRepository<ImageRoom, ImageRoomKey> {

    Mono<ImageRoom> findById(ImageRoomKey id);

}
