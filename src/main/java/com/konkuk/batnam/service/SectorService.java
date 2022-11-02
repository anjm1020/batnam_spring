package com.konkuk.batnam.service;

import com.konkuk.batnam.domain.AirStrip;
import com.konkuk.batnam.domain.Sector;
import com.konkuk.batnam.dto.request.sector.SectorCreateDto;
import com.konkuk.batnam.dto.response.SectorResponseDto;
import com.konkuk.batnam.repository.AirStripRepository;
import com.konkuk.batnam.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SectorService {
    private final SectorRepository sectorRepository;
    private final AirStripRepository airStripRepository;

    @Transactional
    public SectorResponseDto createSector(SectorCreateDto dto) {
        Optional<AirStrip> optional = airStripRepository.findById(dto.getAirStripId());
        if (optional.isEmpty()) return null;
        Sector saved = sectorRepository.save(dto.toEntity(optional.get()));
        return SectorResponseDto.toResponseDto(saved);
    }

}
