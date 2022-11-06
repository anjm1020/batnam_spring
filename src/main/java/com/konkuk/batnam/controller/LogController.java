package com.konkuk.batnam.controller;

import com.konkuk.batnam.dto.request.log.LogCreateDto;
import com.konkuk.batnam.dto.response.ListResponseDto;
import com.konkuk.batnam.dto.response.log.LogListByDayResponseDto;
import com.konkuk.batnam.dto.response.log.LogResponseDto;
import com.konkuk.batnam.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/logs", produces = "application/json; charset=UTF8")
public class LogController {

    private final LogService logService;

    @PostMapping
    public LogResponseDto createLog(@RequestBody LogCreateDto dto) {
        return logService.createLog(dto);
    }

    @GetMapping("/bySector/{sectorId}")
    public ListResponseDto<LogResponseDto> getLogsBySector(@PathVariable Long sectorId, Pageable pageable) {
        return logService.getListBySector(sectorId,pageable);
    }

    @GetMapping("/byStrip/{airStripId}")
    public ListResponseDto<LogResponseDto> getLogsByStrip(@PathVariable Long airStripId, Pageable pageable) {
        return logService.getListByAirStrip(airStripId, pageable);
    }

    @GetMapping("/inWeek")
    public LogListByDayResponseDto getLogsInWeek() {
        return logService.getListInWeek(LocalDateTime.now());
    }
}
