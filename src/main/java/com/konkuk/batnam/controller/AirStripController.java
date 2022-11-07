package com.konkuk.batnam.controller;

import com.konkuk.batnam.dto.request.airstrip.AirStripCreateDto;
import com.konkuk.batnam.dto.request.airstrip.AirStripUpdateDto;
import com.konkuk.batnam.dto.response.AirStripResponseDto;
import com.konkuk.batnam.service.AirStripService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/airstrips", produces = "application/json; charset=UTF8")
public class AirStripController {

    private final AirStripService airstripService;

    @Operation(summary = "활주로 생성",description = "활주로 생성 api")
    @PostMapping
    public AirStripResponseDto createAirStrip(@RequestBody AirStripCreateDto dto) {
        return airstripService.createAirStrip(dto);
    }

    @Operation(summary = "활주로 조회",description = "활주로 조회 api")
    @GetMapping("/{id}")
    public AirStripResponseDto getAirStripById(@PathVariable Long id) {
        return airstripService.findAirStripById(id);
    }

    @Operation(summary = "활주로 업데이트",description = "활주로 자체 컬럼 수정 api(name,startZone,endZone)")
    @PutMapping
    public Long updateAirStrip(@RequestBody AirStripUpdateDto dto) {
        return airstripService.updateAirStrip(dto);
    }

    @Operation(summary = "활주로 삭제",description = "활주로 삭제 api")
    @DeleteMapping("/{id}")
    public void deleteAirStrip(@PathVariable Long id) {
        airstripService.deleteAirStrip(id);
    }

}
