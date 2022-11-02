package com.konkuk.batnam.dto.request.airstrip;

import com.konkuk.batnam.domain.AirStrip;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AirStripUpdateDto {
    private Long id;
    private String name;
    private String startZone;
    private String endZone;

    public void update(AirStrip entity) {
        entity.setName(this.name);
        entity.setStartZone(this.startZone);
        entity.setEndZone(this.endZone);
    }
}
