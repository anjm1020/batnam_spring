package com.konkuk.batnam.service;

import com.konkuk.batnam.domain.Log;
import com.konkuk.batnam.domain.ObservationObject;
import com.konkuk.batnam.domain.Sector;
import com.konkuk.batnam.dto.request.log.LogCreateDto;
import com.konkuk.batnam.dto.response.LogResponseDto;
import com.konkuk.batnam.repository.LogRepository;
import com.konkuk.batnam.repository.ObservationObjectRepository;
import com.konkuk.batnam.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;
    private final SectorRepository sectorRepository;
    private final ObservationObjectRepository objectRepository;

    @Transactional
    public LogResponseDto createLog(LogCreateDto dto) {
        Optional<Sector> optionalSector = sectorRepository.findById(dto.getSectorId());
        Optional<ObservationObject> optionalObject = objectRepository.findById(dto.getObjectId());
        if(optionalSector.isEmpty()) return null;
        if(optionalObject.isEmpty()) return null;
        Log saved = logRepository.save(dto.toEntity(optionalSector.get(),optionalObject.get()));
        return LogResponseDto.toResponseDto(saved);
    }

    @Transactional
    public void deleteLog(Long id) {
        Optional<Log> optional = logRepository.findById(id);
        if(optional.isEmpty()) return;
        logRepository.delete(optional.get());
    }

}
