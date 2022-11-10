package com.konkuk.batnam.controller;

import com.konkuk.batnam.dto.request.sector.SectorCreateDto;
import com.konkuk.batnam.dto.request.sector.SectorUpdateDto;
import com.konkuk.batnam.dto.response.SectorResponseDto;
import com.konkuk.batnam.service.SectorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/sectors", produces = "application/json; charset=UTF8")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SectorController {
    private final SectorService sectorService;

    @Operation(summary = "섹터 생성",description = "섹터 생성 api")
    @PostMapping
    public SectorResponseDto createSector(@RequestBody SectorCreateDto dto) {
        return sectorService.createSector(dto);
    }

    @Operation(summary = "섹터 수정",description = "섹터 수정 api")
    @PutMapping
    public Long updateSector(@RequestBody SectorUpdateDto dto) {
        return sectorService.updateSector(dto);
    }

    @Operation(summary = "섹터 삭제",description = "섹터 삭제 api")
    @DeleteMapping("/{id}")
    public void deleteSector(@PathVariable Long id) {
        sectorService.deleteSector(id);
    }
}
