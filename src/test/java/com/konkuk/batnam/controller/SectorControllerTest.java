package com.konkuk.batnam.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konkuk.batnam.domain.AirStrip;
import com.konkuk.batnam.domain.Sector;
import com.konkuk.batnam.dto.request.airstrip.AirStripCreateDto;
import com.konkuk.batnam.dto.request.sector.SectorCreateDto;
import com.konkuk.batnam.dto.request.sector.SectorUpdateDto;
import com.konkuk.batnam.dto.response.AirStripResponseDto;
import com.konkuk.batnam.dto.response.SectorResponseDto;
import com.konkuk.batnam.repository.AirStripRepository;
import com.konkuk.batnam.repository.SectorRepository;
import com.konkuk.batnam.service.AirStripService;
import com.konkuk.batnam.service.SectorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class SectorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    SectorService sectorService;

    @Autowired
    AirStripService airStripService;

    @Autowired
    SectorRepository sectorRepository;

    @Autowired
    AirStripRepository airStripRepository;

    AirStripResponseDto createAirStrip() {
        return airStripService.createAirStrip(new AirStripCreateDto("name", "s", "e"));
    }

    SectorCreateDto createSectorDto(String name, String camUrl, String x, String y, Long airStripId) {
        return new SectorCreateDto(name, camUrl, x, y, airStripId);
    }

    Sector toEntity(SectorResponseDto dto) {
        return Sector.builder()
                .id(dto.getId())
                .camURL(dto.getCamURL())
                .name(dto.getName())
                .x(dto.getX())
                .y(dto.getY())
                .airStrip(airStripRepository.findById(dto.getAirStripId()).get())
                .build();
    }

    Sector toEntity(SectorCreateDto dto) {
        return Sector.builder()
                .camURL(dto.getCamURL())
                .name(dto.getName())
                .x(dto.getX())
                .y(dto.getY())
                .airStrip(airStripRepository.findById(dto.getAirStripId()).get())
                .build();
    }

    Sector toEntity(SectorUpdateDto dto) {
        return Sector.builder()
                .camURL(dto.getCamURL())
                .name(dto.getName())
                .x(dto.getX())
                .y(dto.getY())
                .airStrip(null)
                .build();
    }


    void checkValidationOfResponseDto(Sector response, Sector comp) {
        Optional<Sector> optionalSector = sectorRepository.findById(response.getId());
        assertEquals(false, optionalSector.isEmpty());
        assertEquals(response.getName(), comp.getName());
        assertEquals(response.getCamURL(), comp.getCamURL());
        assertEquals(response.getX(), comp.getX());
        assertEquals(response.getY(), comp.getY());
        AirStrip airStrip = response.getAirStrip();

        Optional<Sector> optional = airStrip.getSectorList().stream()
                .filter(sector -> sector.getId() == response.getId())
                .findAny();
        assertEquals(false, optional.isEmpty());
        Sector sectorInAirStrip = optional.get();
        assertEquals(sectorInAirStrip.getName(), comp.getName());
        assertEquals(sectorInAirStrip.getCamURL(), comp.getCamURL());
        assertEquals(sectorInAirStrip.getX(), comp.getX());
        assertEquals(sectorInAirStrip.getY(), comp.getY());
    }

    @Test
    void createSector() throws Exception {
        String name = "123";
        String camURL = "url";
        String x = "x";
        String y = "y";
        AirStripResponseDto airStrip = createAirStrip();
        Long airStripId = airStrip.getId();

        SectorCreateDto createDto = createSectorDto(name, camURL, x, y, airStripId);

        String res = mockMvc.perform(post("/sectors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        SectorResponseDto responseDto = objectMapper.readValue(res, SectorResponseDto.class);

        checkValidationOfResponseDto(toEntity(responseDto), toEntity(createDto));
    }

    @Test
    void updateSector() throws Exception {
        String name = "123";
        String camURL = "url";
        String x = "x";
        String y = "y";
        AirStripResponseDto airStrip = createAirStrip();
        Long airStripId = airStrip.getId();

        SectorCreateDto createDto = createSectorDto(name, camURL, x, y, airStripId);
        SectorResponseDto createResponseDto = sectorService.createSector(createDto);

        String newName = "newName";
        SectorUpdateDto updateDto = new SectorUpdateDto(createResponseDto.getId(), newName, camURL, x, y);

        String res = mockMvc.perform(put("/sectors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Long resId = objectMapper.readValue(res, Long.class);

        Optional<Sector> optional = sectorRepository.findById(resId);
        assertEquals(false, optional.isEmpty());
        Sector entity = optional.get();
        checkValidationOfResponseDto(entity, toEntity(updateDto));
    }

    @Test
    void deleteSector() throws Exception {
        String name = "123";
        String camURL = "url";
        String x = "x";
        String y = "y";
        AirStripResponseDto airStrip = createAirStrip();
        Long airStripId = airStrip.getId();

        SectorCreateDto createDto = createSectorDto(name, camURL, x, y, airStripId);
        SectorResponseDto createResponseDto = sectorService.createSector(createDto);

        mockMvc.perform(delete("/sectors/" + createResponseDto.getId()))
                .andExpect(status().isOk());

        Optional<Sector> optional = sectorRepository.findById(createResponseDto.getId());
        assertEquals(true, optional.isEmpty());
    }
}