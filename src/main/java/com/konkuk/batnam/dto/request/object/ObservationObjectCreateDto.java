package com.konkuk.batnam.dto.request.object;

import com.konkuk.batnam.domain.ObservationObject;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ObservationObjectCreateDto {
    private String type;
    private String name;

    public ObservationObject toEntity() {
        return ObservationObject.builder()
                .type(this.type)
                .name(this.name)
                .build();
    }

}
