package com.konkuk.batnam.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konkuk.batnam.domain.AirStrip;
import com.konkuk.batnam.dto.request.airstrip.AirStripCreateDto;
import com.konkuk.batnam.dto.request.airstrip.AirStripUpdateDto;
import com.konkuk.batnam.dto.response.AirStripResponseDto;
import com.konkuk.batnam.repository.AirStripRepository;
import com.konkuk.batnam.repository.SectorRepository;
import com.konkuk.batnam.service.AirStripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class AirStripControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AirStripService airstripService;

    @Autowired
    AirStripRepository airStripRepository;

    @Autowired
    SectorRepository sectorRepository;

    @BeforeEach
    void beforeEach() {
        airStripRepository.deleteAll();
    }

    AirStripResponseDto createAirStrip(AirStripCreateDto createDto) {
        return airstripService.createAirStrip(createDto);
    }

    AirStrip toEntity(AirStripResponseDto responseDto) {
        return AirStrip.builder()
                .id(responseDto.getId())
                .name(responseDto.getName())
                .startZone(responseDto.getStartZone())
                .endZone(responseDto.getEndZone())
                .sectorList(responseDto.getSectorList().stream()
                        .map(sectorResponseDto -> sectorRepository.findById(sectorResponseDto.getId()).get())
                        .collect(Collectors.toList()))
                .build();
    }

    AirStrip toEntity(AirStripUpdateDto updateDto) {
        return AirStrip.builder()
                .id(updateDto.getId())
                .name(updateDto.getName())
                .startZone(updateDto.getStartZone())
                .endZone(updateDto.getEndZone())
                .sectorList(null)
                .build();
    }

    void checkValidationOfResponseDto(AirStrip response, AirStrip comp) {
        assertEquals(true, response.getId() != null);
        assertEquals(comp.getName(), response.getName());
        assertEquals(comp.getStartZone(), response.getStartZone());
        assertEquals(comp.getEndZone(), response.getEndZone());
        if (comp.getSectorList() != null) assertEquals(comp.getSectorList().size(), response.getSectorList().size());
    }

    @Test
    void createAirStrip() throws Exception {
        String name = "NAME";
        String startZone = "startZone";
        String endZone = "endZone";
        AirStripCreateDto createDto = new AirStripCreateDto(name, startZone, endZone);

        String res = mockMvc.perform(post("/airstrips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        AirStripResponseDto responseDto = objectMapper.readValue(res, AirStripResponseDto.class);

        checkValidationOfResponseDto(toEntity(responseDto), createDto.toEntity());
    }

    @Test
    void getAirStrip() throws Exception {
        String name = "NAME";
        String startZone = "startZone";
        String endZone = "endZone";
        AirStripCreateDto createDto = new AirStripCreateDto(name, startZone, endZone);
        AirStripResponseDto airStrip = createAirStrip(createDto);

        String res = mockMvc.perform(get("/airstrips/" + airStrip.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        AirStripResponseDto responseDto = objectMapper.readValue(res, AirStripResponseDto.class);

        checkValidationOfResponseDto(toEntity(responseDto), createDto.toEntity());
    }

    @Test
    void updateAirStrip() throws Exception {
        String name = "NAME";
        String startZone = "startZone";
        String endZone = "endZone";
        AirStripCreateDto createDto = new AirStripCreateDto(name, startZone, endZone);
        AirStripResponseDto responseDto = createAirStrip(createDto);

        String newName = "newName";
        AirStripUpdateDto updateDto = new AirStripUpdateDto(responseDto.getId(), newName, startZone, endZone);

        String res = mockMvc.perform(put("/airstrips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Long resId = objectMapper.readValue(res, Long.class);

        Optional<AirStrip> optional = airStripRepository.findById(resId);
        assertEquals(false, optional.isEmpty());
        AirStrip response = optional.get();
        checkValidationOfResponseDto(response, toEntity(updateDto));
    }

    @Test
    void deleteAirStrip() throws Exception {
        String name = "NAME";
        String startZone = "startZone";
        String endZone = "endZone";
        AirStripCreateDto createDto = new AirStripCreateDto(name, startZone, endZone);

        AirStripResponseDto responseDto = airstripService.createAirStrip(createDto);

        String res = mockMvc.perform(delete("/airstrips/"+responseDto.getId())
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Optional<AirStrip> optional = airStripRepository.findById(responseDto.getId());
        assertEquals(true, optional.isEmpty());
    }
}
