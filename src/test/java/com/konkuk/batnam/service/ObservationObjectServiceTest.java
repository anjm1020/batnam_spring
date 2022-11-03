package com.konkuk.batnam.service;

import com.konkuk.batnam.domain.ObservationObject;
import com.konkuk.batnam.dto.request.object.ObservationObjectCreateDto;
import com.konkuk.batnam.dto.response.ObservationObjectResponseDto;
import com.konkuk.batnam.repository.AirStripRepository;
import com.konkuk.batnam.repository.ObservationObjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ObservationObjectServiceTest {

    @Autowired
    ObservationObjectService objectService;

    @Autowired
    ObservationObjectRepository objectRepository;

    @BeforeEach
    void beforeEach() {
        objectRepository.deleteAll();
    }

    @Test
    void createObject() {
        String type = "BIRD";
        String name = "새";
        ObservationObjectResponseDto object = objectService.createObject(new ObservationObjectCreateDto(type, name));

        Optional<ObservationObject> optional = objectRepository.findById(object.getId());
        assertEquals(false, optional.isEmpty());
        ObservationObject entity = optional.get();
        assertEquals(type, entity.getType());
        assertEquals(name, entity.getName());
        assertEquals(false, entity.getFirstDate().toString().isEmpty());
        assertEquals(false, entity.getLastDate().toString().isEmpty());
    }

    @Test
    void addCount() {
        String type = "BIRD";
        String name = "새";
        ObservationObjectResponseDto object = objectService.createObject(new ObservationObjectCreateDto(type, name));

        ObservationObjectResponseDto dto = objectService.addCount(object.getId());

        Optional<ObservationObject> optional = objectRepository.findById(dto.getId());

        assertEquals(false, optional.isEmpty());
        ObservationObject entity = optional.get();
        assertEquals(type, entity.getType());
        assertEquals(name, entity.getName());
        assertEquals(object.getCount() + 1, entity.getCount());
    }

    @Test
    void deleteObject() {
        String type = "BIRD";
        String name = "새";
        ObservationObjectResponseDto object = objectService.createObject(new ObservationObjectCreateDto(type, name));

        objectService.deleteObject(object.getId());

        Optional<ObservationObject> optional = objectRepository.findById(object.getId());
        assertEquals(true, optional.isEmpty());
    }
}