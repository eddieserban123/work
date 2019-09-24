package com.report.service;

import com.report.repository.ClassRoomPersonsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Log4j2
@AllArgsConstructor
@Service
public class ClassRoomPersonsService {

    private final ClassRoomPersonsRepository classRoomPersonsRepository;

    public void insertPeronsInClassRoom(String classRoomId, LocalDate snapShotDate, String personId) {
        classRoomPersonsRepository.insertPersonInClassRoom(classRoomId, snapShotDate, personId);
    }

}
