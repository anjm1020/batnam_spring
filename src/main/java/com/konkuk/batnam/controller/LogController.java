package com.konkuk.batnam.controller;

import com.konkuk.batnam.dto.request.log.LogCreateDto;
import com.konkuk.batnam.dto.response.ListResponseDto;
import com.konkuk.batnam.dto.response.log.LogListByDayResponseDto;
import com.konkuk.batnam.dto.response.log.LogResponseDto;
import com.konkuk.batnam.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/logs", produces = "application/json; charset=UTF8")
public class LogController {

    private final LogService logService;

    @Operation(summary = "로그 생성 (클라이언트 사용x)",description = "로그 생성 api")
    @PostMapping
    public LogResponseDto createLog(@RequestBody LogCreateDto dto) {
        return logService.createLog(dto);
    }
    @Operation(summary = "섹터 별 로그 조회",description = "섹터 id, 페이지네이션 쿼리 파라미터 받음\n" +
            "page,size만 넣어서 사용")
    @GetMapping("/bySector/{sectorId}")
    public ListResponseDto<LogResponseDto> getLogsBySector(@PathVariable Long sectorId, Pageable pageable) {
        return logService.getListBySector(sectorId,pageable);
    }
    @Operation(summary = "활주로 별 로그 조회",description = "활주로 id, 페이지네이션 쿼리 파라미터 받음\n" +
            "page,size만 넣어서 사용")
    @GetMapping("/byStrip/{airStripId}")
    public ListResponseDto<LogResponseDto> getLogsByStrip(@PathVariable Long airStripId, Pageable pageable) {
        return logService.getListByAirStrip(airStripId, pageable);
    }

    @Operation(summary = "최근 7일 로그 조회",description = "result에 DayOfWeek : log 쌍으로 반환")
    @GetMapping("/inWeek")
    public LogListByDayResponseDto getLogsInWeek() {
        return logService.getListInWeek(LocalDateTime.now());
    }
}
