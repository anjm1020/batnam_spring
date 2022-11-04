package com.konkuk.batnam.dto.response;

import com.konkuk.batnam.domain.AirStrip;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AirStripResponseDto {
    private Long id;
    private String name;
    private String startZone;
    private String endZone;
    private List<SectorResponseDto> sectorList;

    public static AirStripResponseDto toResponseDto(AirStrip entity) {
        return new AirStripResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getStartZone(),
                entity.getEndZone(),
                entity.getSectorList().stream()
                        .map(sector -> SectorResponseDto.toResponseDto(sector))
                        .collect(Collectors.toList())
        );
    }
}
