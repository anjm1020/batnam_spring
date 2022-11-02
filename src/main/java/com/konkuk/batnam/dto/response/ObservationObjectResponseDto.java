package com.konkuk.batnam.dto.response;

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
}
