package com.report.service;

import com.report.repository.ClassRoomKidsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Log4j2
@AllArgsConstructor
@Service
public class ClassRoomKidsService {

    private final ClassRoomKidsRepository classRoomKidsRepository;

    public void insertKidInClassRoom(String classRoomId, LocalDate snapShotDate, String personId) {
        classRoomKidsRepository.insertKidInClassRoom(classRoomId, snapShotDate, personId);
    }

}
