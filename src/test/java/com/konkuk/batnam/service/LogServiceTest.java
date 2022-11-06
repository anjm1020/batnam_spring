package com.konkuk.batnam.service;

import com.konkuk.batnam.domain.Log;
import com.konkuk.batnam.dto.request.airstrip.AirStripCreateDto;
import com.konkuk.batnam.dto.request.log.LogCreateDto;
import com.konkuk.batnam.dto.request.sector.SectorCreateDto;
import com.konkuk.batnam.dto.response.AirStripResponseDto;
import com.konkuk.batnam.dto.response.ListResponseDto;
import com.konkuk.batnam.dto.response.log.LogResponseDto;
import com.konkuk.batnam.dto.response.SectorResponseDto;
import com.konkuk.batnam.repository.LogRepository;
import com.konkuk.batnam.repository.SectorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
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
    SectorService sectorService;
    @Autowired
    SectorRepository sectorRepository;

    @BeforeEach
    void beforeEach() {
        logRepository.deleteAll();
    }

    AirStripResponseDto createAirStrip() {
        return airstripService.createAirStrip(new AirStripCreateDto("name", "startZone", "endZone"));
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

        Long sectorId = sector.getId();

        LogResponseDto saved = logService.createLog(new LogCreateDto("BIRD", captureURL, result, resultURL, sectorId));

        Optional<Log> optional = logRepository.findById(saved.getId());

        assertEquals(false, optional.isEmpty());
        Log entity = optional.get();

        assertEquals(captureURL, entity.getCaptureURL());
        assertEquals(result, entity.getResult());
        assertEquals(resultURL, entity.getResultURL());
        assertEquals(sectorId, entity.getSector().getId());
        assertEquals(2022, entity.getLogDate().getYear());
    }

    @Test
    void getListBySector() {
        AirStripResponseDto airStrip = createAirStrip();
        SectorResponseDto sectorA = createSector(airStrip.getId());
        SectorResponseDto sectorB = createSector(airStrip.getId());

        int aCount = 20;
        int bCount = 10;
        for (int i = 0; i < aCount; i++) {
            logService.createLog(new LogCreateDto("BIRD", "", "", "", sectorA.getId()));
        }

        for (int i = 0; i < bCount; i++) {
            logService.createLog(new LogCreateDto("BIRD", "", "", "", sectorB.getId()));
        }

        int aPage = 1;
        int aPageSize = 7;
        ListResponseDto listBySectorA = logService.getListBySector(sectorA.getId(), PageRequest.of(aPage, aPageSize));
        assertEquals(aPageSize, listBySectorA.getPageSize());
        assertEquals(aPage,listBySectorA.getPage());
        assertEquals(aPageSize, listBySectorA.getResult().size());
        assertEquals(true, listBySectorA.getResult().stream()
                .filter(log -> ((LogResponseDto) log).getSectorId() == sectorA.getId())
                .count()==aPageSize);
    }

    @Test
    void getListByAirStrip() {
        AirStripResponseDto airStripA = createAirStrip();
        AirStripResponseDto airStripB = createAirStrip();
        SectorResponseDto sectorA = createSector(airStripA.getId());
        SectorResponseDto sectorB = createSector(airStripB.getId());

        int aCount = 20;
        int bCount = 10;
        for (int i = 0; i < aCount; i++) {
            logService.createLog(new LogCreateDto("BIRD", "", "", "", sectorA.getId()));
        }

        for (int i = 0; i < bCount; i++) {
            logService.createLog(new LogCreateDto("BIRD", "", "", "", sectorB.getId()));
        }

        int pageA = 0;
        int sizeA = 11;
        ListResponseDto listByAirStripA = logService.getListByAirStrip(airStripA.getId(), PageRequest.of(pageA, sizeA));
        assertEquals(sizeA, listByAirStripA.getResult().size());


        int pageB = 1;
        int sizeB = 4;
        ListResponseDto listByAirStripB = logService.getListByAirStrip(airStripB.getId(), PageRequest.of(pageB, sizeB));
        assertEquals(sizeB, listByAirStripB.getResult().size());
    }

    @Test
    void deleteLog() {
    }

}