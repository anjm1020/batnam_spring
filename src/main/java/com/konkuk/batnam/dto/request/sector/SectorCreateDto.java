package com.konkuk.batnam.dto.request.sector;

import com.konkuk.batnam.domain.AirStrip;
import com.konkuk.batnam.domain.Sector;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SectorCreateDto {
    private String name;
    private String camURL;
    private String x;
    private String y;
    private Long airStripId;

    public Sector toEntity(AirStrip airStrip) {
        return Sector.builder()
                .name(this.name)
                .camURL(this.camURL)
                .x(this.x)
                .y(this.y)
                .airStrip(airStrip)
                .build();
    }

}
