package com.konkuk.batnam.service;

import com.konkuk.batnam.domain.AirStrip;
import com.konkuk.batnam.domain.Sector;
import com.konkuk.batnam.dto.request.sector.SectorCreateDto;
import com.konkuk.batnam.dto.request.sector.SectorUpdateDto;
import com.konkuk.batnam.dto.response.SectorResponseDto;
import com.konkuk.batnam.repository.AirStripRepository;
import com.konkuk.batnam.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        AirStrip airStrip = optional.get();
        Sector saved = sectorRepository.save(dto.toEntity(airStrip));
        airStrip.addSector(saved);
        return SectorResponseDto.toResponseDto(saved);
    }

    @Transactional
    public Long updateSector(SectorUpdateDto dto) {
        Optional<Sector> optional = sectorRepository.findById(dto.getId());
        if (optional.isEmpty()) return null;
        Sector entity = optional.get();
        dto.update(entity);
        return entity.getId();
    }

    @Transactional
    public void deleteSector(Long id) {
        Optional<Sector> optional = sectorRepository.findById(id);
        if (optional.isEmpty()) return;
        Sector sector = optional.get();
        sector.getAirStrip().deleteSector(sector);
        sectorRepository.delete(sector);
    }


}
