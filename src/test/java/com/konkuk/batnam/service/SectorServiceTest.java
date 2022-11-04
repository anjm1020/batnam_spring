package com.konkuk.batnam.service;

import com.konkuk.batnam.domain.Sector;
import com.konkuk.batnam.dto.request.airstrip.AirStripCreateDto;
import com.konkuk.batnam.dto.request.sector.SectorCreateDto;
import com.konkuk.batnam.dto.request.sector.SectorUpdateDto;
import com.konkuk.batnam.dto.response.AirStripResponseDto;
import com.konkuk.batnam.dto.response.SectorResponseDto;
import com.konkuk.batnam.repository.AirStripRepository;
import com.konkuk.batnam.repository.SectorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SectorServiceTest {

    @Autowired
    SectorService sectorService;
    @Autowired
    AirStripService airstripService;

    @Autowired
    SectorRepository sectorRepository;
    @Autowired
    AirStripRepository airStripRepository;

    @BeforeEach
    void beforeEach() {
        sectorRepository.deleteAll();
        airStripRepository.deleteAll();
    }

    AirStripResponseDto createAirStrip() {
        String name = "test";
        String startZone = "N12";
        String endZone = "A13";
        return airstripService.createAirStrip(new AirStripCreateDto(name, startZone, endZone));
    }

    @Test
    void createSector() {
        AirStripResponseDto airStrip = createAirStrip();
        String name = "test";
        String camURL = "MY_URL";
        String x = "111";
        String y = "222";

        SectorResponseDto res = sectorService.createSector(new SectorCreateDto(name, camURL, x, y, airStrip.getId()));
        Optional<Sector> optional = sectorRepository.findById(res.getId());

        assertEquals(false, optional.isEmpty());
        Sector entity = optional.get();
        assertEquals(name, entity.getName());
        assertEquals(camURL, entity.getCamURL());
        assertEquals(x, entity.getX());
        assertEquals(y, entity.getY());
        assertEquals(airStrip.getName(), entity.getAirStrip().getName());
    }

    @Test
    void updateSector() {
        AirStripResponseDto airStrip = createAirStrip();
        String name = "test";
        String camURL = "MY_URL";
        String x = "111";
        String y = "222";

        SectorResponseDto res = sectorService.createSector(new SectorCreateDto(name, camURL, x, y, airStrip.getId()));
        String newName = "TEST1";
        String newX = "123213";
        Long id = sectorService.updateSector(new SectorUpdateDto(res.getId(), newName, camURL, newX, y));

        Optional<Sector> optional = sectorRepository.findById(id);

        assertEquals(false, optional.isEmpty());
        Sector entity = optional.get();
        assertEquals(newName, entity.getName());
        assertEquals(camURL, entity.getCamURL());
        assertEquals(newX, entity.getX());
        assertEquals(y, entity.getY());
    }

    @Test
    void deleteSector() {
        AirStripResponseDto airStrip = createAirStrip();
        String name = "test";
        String camURL = "MY_URL";
        String x = "111";
        String y = "222";

        SectorResponseDto res = sectorService.createSector(new SectorCreateDto(name, camURL, x, y, airStrip.getId()));

        sectorService.deleteSector(res.getId());

        Optional<Sector> optional = sectorRepository.findById(res.getId());
        assertEquals(true, optional.isEmpty());
    }
}