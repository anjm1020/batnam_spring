package com.konkuk.batnam.service;

import com.konkuk.batnam.domain.ObservationObject;
import com.konkuk.batnam.dto.request.object.ObservationObjectCreateDto;
import com.konkuk.batnam.dto.response.ObservationObjectResponseDto;
import com.konkuk.batnam.repository.ObservationObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ObservationObjectService {
    private final ObservationObjectRepository objectRepository;

    @Transactional
    public ObservationObjectResponseDto createObject(ObservationObjectCreateDto dto) {
        ObservationObject saved = objectRepository.save(dto.toEntity());
        return ObservationObjectResponseDto.toResponseDto(saved);
    }

    @Transactional
    public ObservationObjectResponseDto addCount(Long id) {
        Optional<ObservationObject> optional = objectRepository.findById(id);
        if (optional.isEmpty()) return null;
        ObservationObject entity = optional.get();
        entity.addCount();
        return ObservationObjectResponseDto.toResponseDto(entity);
    }

    @Transactional
    public void deleteObject(Long id) {
        Optional<ObservationObject> optional = objectRepository.findById(id);
        if (optional.isEmpty()) return;
        objectRepository.delete(optional.get());
    }

}
