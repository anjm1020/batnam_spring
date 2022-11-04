package com.konkuk.batnam.service;

import com.konkuk.batnam.domain.AirStrip;
import com.konkuk.batnam.dto.request.airstrip.AirStripCreateDto;
import com.konkuk.batnam.dto.request.airstrip.AirStripUpdateDto;
import com.konkuk.batnam.dto.response.AirStripResponseDto;
import com.konkuk.batnam.repository.AirStripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AirStripServiceTest {

    @Autowired
    private AirStripService airstripService;

    @Autowired
    private AirStripRepository airStripRepository;

    @BeforeEach
    void beforeEach() {
        airStripRepository.deleteAll();
    }

    @Test
    void createAirStrip() {
        String name = "test";
        String startZone = "N12";
        String endZone = "A13";

        AirStripResponseDto saved = airstripService.createAirStrip(new AirStripCreateDto(name, startZone, endZone));

        Optional<AirStrip> optional = airStripRepository.findById(saved.getId());

        assertEquals(optional.isEmpty(), false);
        AirStrip entity = optional.get();
        assertEquals(entity.getId(), saved.getId());
        assertEquals(entity.getName(), name);
        assertEquals(entity.getStartZone(), startZone);
        assertEquals(entity.getEndZone(), endZone);
        assertEquals(entity.getSectorList().size(), 0);
    }

    @Test
    void findAirStripById() {
        String name = "test";
        String startZone = "N12";
        String endZone = "A13";

        AirStripResponseDto saved = airstripService.createAirStrip(new AirStripCreateDto(name, startZone, endZone));

        AirStripResponseDto response = airstripService.findAirStripById(saved.getId());

        assertEquals(saved.getId(), response.getId());
        assertEquals(name, response.getName());
        assertEquals(startZone, response.getStartZone());
        assertEquals(endZone, response.getEndZone());
        assertEquals(0, response.getSectorList().size());
    }

    @Test
    void updateAirStrip() {
        String name = "test";
        String startZone = "N12";
        String endZone = "A13";

        AirStripResponseDto saved = airstripService.createAirStrip(new AirStripCreateDto(name, startZone, endZone));
        String modifiedEndZone = "B13";
        Long id = airstripService.updateAirStrip(new AirStripUpdateDto(saved.getId(), name, startZone, modifiedEndZone));

        assertEquals(id, saved.getId());
        Optional<AirStrip> optional = airStripRepository.findById(id);

        assertEquals(optional.isEmpty(), false);
        AirStrip entity = optional.get();
        assertEquals(entity.getId(), saved.getId());
        assertEquals(entity.getName(), name);
        assertEquals(entity.getStartZone(), startZone);
        assertEquals(entity.getEndZone(), modifiedEndZone);
        assertEquals(entity.getSectorList().size(), 0);
    }

    @Test
    void deleteAirStrip() {
        String name = "test";
        String startZone = "N12";
        String endZone = "A13";

        AirStripResponseDto saved = airstripService.createAirStrip(new AirStripCreateDto(name, startZone, endZone));

        airstripService.deleteAirStrip(saved.getId());

        Optional<AirStrip> optional = airStripRepository.findById(saved.getId());
        assertEquals(true, optional.isEmpty());
    }
}