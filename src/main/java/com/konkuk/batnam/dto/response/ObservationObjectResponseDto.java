package com.konkuk.batnam.dto.response;

import com.konkuk.batnam.domain.ObservationObject;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ObservationObjectResponseDto {
    private Long id;
    private String type;
    private String name;
    private Integer count;
    private String firstDate;
    private String lastDate;

    public static ObservationObjectResponseDto toResponseDto(ObservationObject entity) {
        return new ObservationObjectResponseDto(
                entity.getId(),
                entity.getType(),
                entity.getName(),
                entity.getCount(),
                entity.getFirstDate().toString(),
                entity.getLastDate().toString()
        );
    }
}
