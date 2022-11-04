package com.konkuk.batnam.service;

import com.konkuk.batnam.domain.Log;
import com.konkuk.batnam.dto.request.airstrip.AirStripCreateDto;
import com.konkuk.batnam.dto.request.log.LogCreateDto;
import com.konkuk.batnam.dto.request.object.ObservationObjectCreateDto;
import com.konkuk.batnam.dto.request.sector.SectorCreateDto;
import com.konkuk.batnam.dto.response.AirStripResponseDto;
import com.konkuk.batnam.dto.response.LogResponseDto;
import com.konkuk.batnam.dto.response.ObservationObjectResponseDto;
import com.konkuk.batnam.dto.response.SectorResponseDto;
import com.konkuk.batnam.repository.LogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LogServiceTest {

    @Autowired
    LogService logService;
    @Autowired
    LogRepository logRepository;

    @Autowired
    AirStripService airstripService;
    @Autowired
    ObservationObjectService objectService;
    @Autowired
    SectorService sectorService;

    @BeforeEach
    void beforeEach() {
        logRepository.deleteAll();
    }

    AirStripResponseDto createAirStrip() {
        return airstripService.createAirStrip(new AirStripCreateDto("name", "startZone", "endZone"));
    }

    ObservationObjectResponseDto createObject() {
        return objectService.createObject(new ObservationObjectCreateDto("BIRD", "name"));
    }

    SectorResponseDto createSector(Long airstripId) {
        return sectorService.createSector(new SectorCreateDto("name", "camURL", "x", "y", airstripId));
    }

    @Test
    void createLog() {
        String captureURL = "test";
        String result = "test";
        String resultURL = "test";

        SectorResponseDto sector = createSector(createAirStrip().getId());
        ObservationObjectResponseDto object = createObject();

        Long sectorId = sector.getId();
        Long objectId = object.getId();

        LogResponseDto saved = logService.createLog(new LogCreateDto(captureURL, result, resultURL, sectorId, objectId));

        Optional<Log> optional = logRepository.findById(saved.getId());

        assertEquals(false, optional.isEmpty());
        Log entity = optional.get();

        assertEquals(captureURL, entity.getCaptureURL());
        assertEquals(result, entity.getResult());
        assertEquals(resultURL, entity.getResultURL());
        assertEquals(sectorId, entity.getSector().getId());
        assertEquals(objectId, entity.getObject().getId());
        assertEquals(2022,entity.getLogDate().getYear());
    }

    @Test
    void deleteLog() {
    }
}