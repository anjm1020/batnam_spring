package com.konkuk.batnam.dto.response;

import com.konkuk.batnam.domain.Responder;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponderResponseDto  {
    private Long id;
    private Long airStripId;
    private String type;
    private String name;
    private String detail;
    private String dest;

    public static ResponderResponseDto toResponseDto(Responder responder) {
        return new ResponderResponseDto(
                responder.getId(),
                responder.getAirStrip().getId(),
                responder.getType(),
                responder.getName(),
                responder.getDetail(),
                responder.getDest()
                );
    }

}
