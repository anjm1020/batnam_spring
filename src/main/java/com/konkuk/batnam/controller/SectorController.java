package com.konkuk.batnam.controller;

import com.konkuk.batnam.dto.request.sector.SectorCreateDto;
import com.konkuk.batnam.dto.request.sector.SectorUpdateDto;
import com.konkuk.batnam.dto.response.SectorResponseDto;
import com.konkuk.batnam.service.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/sectors", produces = "application/json; charset=UTF8")
public class SectorController {
    private final SectorService sectorService;

    @PostMapping
    public SectorResponseDto createSector(@RequestBody SectorCreateDto dto) {
        return sectorService.createSector(dto);
    }

    @PutMapping
    public Long updateSector(@RequestBody SectorUpdateDto dto) {
        return sectorService.updateSector(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteSector(@PathVariable Long id) {
        sectorService.deleteSector(id);
    }
}
