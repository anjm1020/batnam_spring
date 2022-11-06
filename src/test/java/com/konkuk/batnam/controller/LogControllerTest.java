package com.konkuk.batnam.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konkuk.batnam.domain.Log;
import com.konkuk.batnam.dto.request.airstrip.AirStripCreateDto;
import com.konkuk.batnam.dto.request.log.LogCreateDto;
import com.konkuk.batnam.dto.request.sector.SectorCreateDto;
import com.konkuk.batnam.dto.response.AirStripResponseDto;
import com.konkuk.batnam.dto.response.ListResponseDto;
import com.konkuk.batnam.dto.response.log.LogResponseDto;
import com.konkuk.batnam.dto.response.SectorResponseDto;
import com.konkuk.batnam.repository.LogRepository;
import com.konkuk.batnam.service.AirStripService;
import com.konkuk.batnam.service.LogService;
import com.konkuk.batnam.service.SectorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class LogControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    LogService logService;
    @Autowired
    LogRepository logRepository;

    @Autowired
    SectorService sectorService;
    @Autowired
    AirStripService airStripService;

    AirStripResponseDto createStrip() {
        return airStripService.createAirStrip(new AirStripCreateDto("n", "sz", "ez"));
    }

    SectorResponseDto createSector(Long stripId) {
        return sectorService.createSector(new SectorCreateDto("n", "a", "b", "c", stripId));
    }

    @Test
    void createLog() throws Exception {
        AirStripResponseDto strip = createStrip();
        SectorResponseDto sector = createSector(strip.getId());
        LogCreateDto createDto = new LogCreateDto("oName", "url", "result", "rUrl", sector.getId());

        String res = mockMvc.perform(post("/logs")
                        .content(objectMapper.writeValueAsString(createDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        LogResponseDto responseDto = objectMapper.readValue(res, LogResponseDto.class);

        Optional<Log> optional = logRepository.findById(responseDto.getId());
        assertEquals(false, optional.isEmpty());
        Log entity = optional.get();
        assertEquals(createDto.getObjectName(), entity.getObjectName());
        assertEquals(createDto.getCaptureURL(), entity.getCaptureURL());
        assertEquals(createDto.getResult(), entity.getResult());
        assertEquals(createDto.getResultURL(), entity.getResultURL());
        assertEquals(sector.getId(), entity.getSector().getId());
    }

    @Test
    void getLogsByStrip() throws Exception {
        AirStripResponseDto stripA = createStrip();
        AirStripResponseDto stripB = createStrip();
        SectorResponseDto sectorA = createSector(stripA.getId());
        SectorResponseDto sectorB = createSector(stripB.getId());

        int aCount = 30;
        for (int i = 0; i < aCount; i++) {
            logService.createLog(new LogCreateDto("oN", "cUrl", "res", "resURL", sectorA.getId()));
        }

        int bCount = 15;
        for (int i = 0; i < bCount; i++) {
            logService.createLog(new LogCreateDto("oN", "cUrl", "res", "resURL", sectorB.getId()));
        }

        String res = mockMvc.perform(get("/logs/byStrip/" + stripA.getId())
                        .queryParam("page","2")
                        .queryParam("size","7"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();

        ListResponseDto responseDto = objectMapper.readValue(res, ListResponseDto.class);
    }

    @Test
    void getLogsBySector() {
    }
}