package com.konkuk.batnam.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konkuk.batnam.domain.AirStrip;
import com.konkuk.batnam.domain.Responder;
import com.konkuk.batnam.dto.request.airstrip.AirStripCreateDto;
import com.konkuk.batnam.dto.request.responder.ResponderCreateDto;
import com.konkuk.batnam.dto.request.responder.ResponderUpdateDto;
import com.konkuk.batnam.dto.response.AirStripResponseDto;
import com.konkuk.batnam.dto.response.ResponderResponseDto;
import com.konkuk.batnam.repository.AirStripRepository;
import com.konkuk.batnam.repository.ResponderRepository;
import com.konkuk.batnam.service.AirStripService;
import com.konkuk.batnam.service.ResponderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class ResponderControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ResponderService responderService;
    @Autowired
    ResponderRepository responderRepository;
    @Autowired
    AirStripService airStripService;
    @Autowired
    AirStripRepository airStripRepository;

    @BeforeEach
    void beforeEach() {
        responderRepository.deleteAll();
        airStripRepository.deleteAll();
    }

    AirStripResponseDto createAirStrip() {
        return airStripService.createAirStrip(new AirStripCreateDto("name", "sz", "ez"));
    }

    ResponderCreateDto createResponderDto(Long airId) {
        return new ResponderCreateDto("type", "name", "detail", "dst", airId);
    }

    @Test
    void createResponder() throws Exception {
        AirStripResponseDto airStrip = createAirStrip();
        ResponderCreateDto createDto = createResponderDto(airStrip.getId());

        String res = mockMvc.perform(post("/responders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ResponderResponseDto responseDto = objectMapper.readValue(res, ResponderResponseDto.class);

        Optional<Responder> optional = responderRepository.findById(responseDto.getId());
        assertEquals(false, optional.isEmpty());
        Responder entity = optional.get();
        assertEquals(createDto.getType(), entity.getType());
        assertEquals(createDto.getName(), entity.getName());
        assertEquals(createDto.getDetail(), entity.getDetail());
        assertEquals(createDto.getDest(), entity.getDest());
        assertEquals(airStrip.getId(), entity.getAirStrip().getId());

    }

    @Test
    void updateResponder() throws Exception {
        AirStripResponseDto airStrip = createAirStrip();
        ResponderCreateDto createDto = createResponderDto(airStrip.getId());
        ResponderResponseDto created = responderService.createResponder(createDto);

        String newName = "newName";
        String newDetail = "newDetail";

        ResponderUpdateDto updateDto = new ResponderUpdateDto(created.getId(), created.getType(), newName, newDetail, created.getDest());

        String res = mockMvc.perform(put("/responders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Long resId = objectMapper.readValue(res, Long.class);

        Optional<Responder> optional = responderRepository.findById(resId);
        assertEquals(false, optional.isEmpty());
        Responder entity = optional.get();
        assertEquals(updateDto.getType(), entity.getType());
        assertEquals(updateDto.getName(), entity.getName());
        assertEquals(updateDto.getDetail(), entity.getDetail());
        assertEquals(updateDto.getDest(), entity.getDest());
        assertEquals(airStrip.getId(), entity.getAirStrip().getId());
    }

    @Test
    void deleteResponder() throws Exception {
        AirStripResponseDto airStrip = createAirStrip();
        ResponderCreateDto createDto = createResponderDto(airStrip.getId());

        ResponderResponseDto responseDto = responderService.createResponder(createDto);

        mockMvc.perform(delete("/responders/" + responseDto.getId()))
                .andExpect(status().isOk());
        Optional<Responder> optional = responderRepository.findById(responseDto.getId());
        assertEquals(true, optional.isEmpty());

        Optional<AirStrip> optionalAirStrip = airStripRepository.findById(airStrip.getId());
        assertEquals(false, optionalAirStrip.isEmpty());
        AirStrip airStripEntity = optionalAirStrip.get();
        List<Responder> allByAirStrip = responderRepository.findAllByAirStrip(airStripEntity);

        Optional<Responder> any = allByAirStrip.stream().filter(sector -> sector.getId() == responseDto.getId())
                .findAny();
        assertEquals(true, any.isEmpty());
    }
}