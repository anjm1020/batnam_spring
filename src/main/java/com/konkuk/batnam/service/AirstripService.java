package com.konkuk.batnam.service;

import com.konkuk.batnam.domain.AirStrip;
import com.konkuk.batnam.dto.request.airstrip.AirStripCreateDto;
import com.konkuk.batnam.dto.request.airstrip.AirStripUpdateDto;
import com.konkuk.batnam.dto.response.AirStripResponseDto;
import com.konkuk.batnam.repository.AirStripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirstripService {
    private final AirStripRepository airStripRepository;

    @Transactional
    public AirStripResponseDto createAirStrip(AirStripCreateDto dto) {
        AirStrip saved = airStripRepository.save(dto.toEntity());
        return AirStripResponseDto.toResponseDto(saved);
    }

    @Transactional
    public AirStripResponseDto findAirStripById(Long id) {
        Optional<AirStrip> optional = airStripRepository.findById(id);
        if (optional.isEmpty()) return null;
        return AirStripResponseDto.toResponseDto(optional.get());
    }

    @Transactional
    public Long updateAirStrip(AirStripUpdateDto dto) {
        Optional<AirStrip> optional = airStripRepository.findById(dto.getId());
        if(optional.isEmpty()) return null;
        AirStrip entity = optional.get();
        dto.update(entity);
        return entity.getId();
    }

    @Transactional
    public void deleteAirStrip(Long id) {
        Optional<AirStrip> optional = airStripRepository.findById(id);
        if (optional.isEmpty()) {
            return;
        }
        airStripRepository.delete(optional.get());
    }
}
