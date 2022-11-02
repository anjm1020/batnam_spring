package com.konkuk.batnam.dto.response;

import com.konkuk.batnam.domain.Sector;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @ToString
public class SectorResponseDto {
    private Long id;
    private String name;
    private String camURL;
    private String x;
    private String y;
    private Long airStripId;

    public static SectorResponseDto toResponseDto(Sector sector) {
        return new SectorResponseDto(
                sector.getId(),
                sector.getName(),
                sector.getCamURL(),
                sector.getX(),
                sector.getY(),
                sector.getAirStrip().getId()
        );
    }
}
