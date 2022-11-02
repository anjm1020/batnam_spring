package com.konkuk.batnam.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AirStripResponseDto {
    private Long id;
    private String code;
    private String name;
    private String direction;
    private String lastDate;
    private List<Long> sectorList;
}
