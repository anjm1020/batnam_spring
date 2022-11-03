package com.konkuk.batnam.dto.request.sector;

import com.konkuk.batnam.domain.Sector;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SectorUpdateDto {
    private Long id;
    private String name;
    private String camURL;
    private String x;
    private String y;

    public void update(Sector entity) {
        entity.setName(this.name);
        entity.setCamURL(this.camURL);
        entity.setX(this.x);
        entity.setY(this.y);
    }
}
