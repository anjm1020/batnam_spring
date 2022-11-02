package com.konkuk.batnam.dto.response;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @ToString
public class SectorResponseDto {
    private Long id;
    private String name;
    private String camURL;
    private String sequence;
    private Long airStripId;
}
