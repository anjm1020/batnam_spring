package com.konkuk.batnam.service;

import com.konkuk.batnam.domain.AirStrip;
import com.konkuk.batnam.domain.Log;
import com.konkuk.batnam.domain.Sector;
import com.konkuk.batnam.dto.request.log.LogCreateDto;
import com.konkuk.batnam.dto.response.ListResponseDto;
import com.konkuk.batnam.dto.response.log.LogListByDayResponseDto;
import com.konkuk.batnam.dto.response.log.LogResponseDto;
import com.konkuk.batnam.repository.AirStripRepository;
import com.konkuk.batnam.repository.LogRepository;
import com.konkuk.batnam.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;
    private final SectorRepository sectorRepository;

    private final AirStripRepository airStripRepository;

    private ListResponseDto toResponseDto(List<LogResponseDto> list, int originalCount, Pageable pageable) {
        return ListResponseDto.toListResponse(list, originalCount, pageable);
    }

    @Transactional
    public LogResponseDto createLog(LogCreateDto dto) {
        Optional<Sector> optionalSector = sectorRepository.findById(dto.getSectorId());
        if (optionalSector.isEmpty()) return null;
        Log saved = logRepository.save(dto.toEntity(optionalSector.get()));
        return LogResponseDto.toResponseDto(saved);
    }

    @Transactional
    public LogListByDayResponseDto getListInWeek(LocalDateTime now) {
        LocalDateTime before = now.minusDays(6);
        LocalDateTime startTime = LocalDateTime.of(before.getYear(), before.getMonth(), before.getDayOfMonth(), 0, 0);
        LocalDateTime endTime = startTime.plusDays(1);
        Map<String, List<LogResponseDto>> map = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            map.put(startTime.getDayOfWeek().toString(), logRepository.findAllByLogDateBetween(startTime, endTime).stream()
                    .map(log -> LogResponseDto.toResponseDto(log))
                    .collect(Collectors.toList()));
            startTime = startTime.plusDays(1);
            endTime = endTime.plusDays(1);
        }
        return new LogListByDayResponseDto(map);
    }

    @Transactional
    public ListResponseDto getListBySector(Long sectorId, Pageable pageable) {
        Optional<Sector> optionalSector = sectorRepository.findById(sectorId);
        if (optionalSector.isEmpty()) return null;
        Sector sector = optionalSector.get();
        List<LogResponseDto> entityList = logRepository.findAllBySector(sector, pageable).stream()
                .map(log -> LogResponseDto.toResponseDto(log))
                .collect(Collectors.toList());
        return toResponseDto(entityList, logRepository.countAllBySector(sector), pageable);
    }

    @Transactional
    public ListResponseDto getListByAirStrip(Long airStripId, Pageable pageable) {
        Optional<AirStrip> optionalAirStrip = airStripRepository.findById(airStripId);
        if (optionalAirStrip.isEmpty()) return null;
        AirStrip airStripEntity = optionalAirStrip.get();
        List<Sector> allByAirStrip = sectorRepository.findAllByAirStrip(airStripEntity);

        List<Log> res = new ArrayList<>();
        for (Sector sector : allByAirStrip) {
            res.addAll(logRepository.findAllBySector(sector));
        }
        res.sort(new Comparator<Log>() {
            @Override
            public int compare(Log o1, Log o2) {
                return o1.getLogDate().isAfter(o2.getLogDate()) ? -1 : 1;
            }
        });
        if (pageable.getPageSize() >= res.size()) {
            return toResponseDto(res.stream()
                            .map(log -> LogResponseDto.toResponseDto(log))
                            .collect(Collectors.toList())
                    , res.size(), pageable);
        }
        int startPoint = pageable.getPageSize() * (pageable.getPageNumber() - 1);
        startPoint = startPoint > 0 ? startPoint : 0;
        List<LogResponseDto> entityList = res.subList(startPoint, startPoint + pageable.getPageSize())
                .stream().map(Log -> LogResponseDto.toResponseDto(Log))
                .collect(Collectors.toList());
        return toResponseDto(entityList, res.size(), pageable);
    }

    @Transactional
    public void deleteLog(Long id) {
        Optional<Log> optional = logRepository.findById(id);
        if (optional.isEmpty()) return;
        logRepository.delete(optional.get());
    }

}
