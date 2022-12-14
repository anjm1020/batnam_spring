package com.konkuk.batnam.dto.request.airstrip;

import com.konkuk.batnam.domain.AirStrip;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AirStripCreateDto {
    private String name;
    private String startZone;
    private String endZone;

    public AirStrip toEntity() {
        return AirStrip.builder()
                .name(this.name)
                .startZone(this.startZone)
                .endZone(this.endZone)
                .build();
    }

}
