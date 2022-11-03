package com.konkuk.batnam.service;

import com.konkuk.batnam.domain.Responder;
import com.konkuk.batnam.dto.request.airstrip.AirStripCreateDto;
import com.konkuk.batnam.dto.request.responder.ResponderCreateDto;
import com.konkuk.batnam.dto.request.responder.ResponderUpdateDto;
import com.konkuk.batnam.dto.response.AirStripResponseDto;
import com.konkuk.batnam.dto.response.ResponderResponseDto;
import com.konkuk.batnam.repository.AirStripRepository;
import com.konkuk.batnam.repository.ResponderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ResponderServiceTest {


    @Autowired
    ResponderService responderService;

    @Autowired
    ResponderRepository responderRepository;

    @Autowired
    AirstripService airstripService;

    @Autowired
    AirStripRepository airStripRepository;

    @BeforeEach
    void beforeEach() {
        responderRepository.deleteAll();
        airStripRepository.deleteAll();
    }

    AirStripResponseDto createAirStrip() {
        String name = "test";
        String startZone = "N12";
        String endZone = "A13";
        return airstripService.createAirStrip(new AirStripCreateDto(name, startZone, endZone));
    }

    @Test
    void createResponder() {
        AirStripResponseDto airStrip = createAirStrip();
        String type = "EMAIL";
        String name = "NAME";
        String detail = "JAEMIN";
        String dest = "010-0000-0000";
        ResponderResponseDto saved = responderService.createResponder(new ResponderCreateDto(
                type,
                name,
                detail,
                dest,
                airStrip.getId()
        ));

        Optional<Responder> optional = responderRepository.findById(saved.getId());
        assertEquals(false, optional.isEmpty());
        Responder entity = optional.get();
        assertEquals(type, entity.getType());
        assertEquals(name, entity.getName());
        assertEquals(detail, entity.getDetail());
        assertEquals(dest, entity.getDest());
        assertEquals(airStrip.getId(), entity.getAirStrip().getId());
    }

    @Test
    void updateResponder() {
        AirStripResponseDto airStrip = createAirStrip();
        String type = "EMAIL";
        String name = "NAME";
        String detail = "JAEMIN";
        String dest = "010-0000-0000";
        ResponderResponseDto saved = responderService.createResponder(new ResponderCreateDto(
                type,
                name,
                detail,
                dest,
                airStrip.getId()
        ));
        String newName = "NAME22";
        String newDetail = "JAEMIN222";
        Long id = responderService.updateResponder(new ResponderUpdateDto(saved.getId(), type, newName, newDetail, dest));

        Optional<Responder> optional = responderRepository.findById(id);
        assertEquals(false, optional.isEmpty());
        Responder entity = optional.get();
        assertEquals(type, entity.getType());
        assertEquals(newName, entity.getName());
        assertEquals(newDetail, entity.getDetail());
        assertEquals(dest, entity.getDest());
        assertEquals(airStrip.getId(), entity.getAirStrip().getId());
    }

    @Test
    void deleteResponder() {
        AirStripResponseDto airStrip = createAirStrip();
        String type = "EMAIL";
        String name = "NAME";
        String detail = "JAEMIN";
        String dest = "010-0000-0000";
        ResponderResponseDto saved = responderService.createResponder(new ResponderCreateDto(
                type,
                name,
                detail,
                dest,
                airStrip.getId()
        ));

        responderService.deleteResponder(saved.getId());

        Optional<Responder> optional = responderRepository.findById(saved.getId());
        assertEquals(true, optional.isEmpty());
    }
}