package com.konkuk.batnam.dto.request.responder;

import com.konkuk.batnam.domain.AirStrip;
import com.konkuk.batnam.domain.Responder;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponderCreateDto {
    private String type;
    private String name;
    private String detail;
    private String dest;
    private Long airStripId;

    public Responder toEntity(AirStrip airStrip) {
        return Responder.builder()
                .type(this.type)
                .name(this.name)
                .detail(this.detail)
                .dest(this.dest)
                .airStrip(airStrip)
                .build();
    }
}
