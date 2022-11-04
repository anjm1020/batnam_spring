package com.konkuk.batnam.controller;

import com.konkuk.batnam.dto.request.airstrip.AirStripCreateDto;
import com.konkuk.batnam.dto.request.airstrip.AirStripUpdateDto;
import com.konkuk.batnam.dto.response.AirStripResponseDto;
import com.konkuk.batnam.service.AirStripService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/airstrips", produces = "application/json; charset=UTF8")
public class AirStripController {

    private final AirStripService airstripService;

    @PostMapping
    public AirStripResponseDto createAirStrip(@RequestBody AirStripCreateDto dto) {
        return airstripService.createAirStrip(dto);
    }

    @GetMapping("/{id}")
    public AirStripResponseDto getAirStripById(@PathVariable Long id) {
        return airstripService.findAirStripById(id);
    }

    @PutMapping
    public Long updateAirStrip(@RequestBody AirStripUpdateDto dto) {
        return airstripService.updateAirStrip(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteAirStrip(@PathVariable Long id) {
        airstripService.deleteAirStrip(id);
    }

}
