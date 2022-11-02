package com.konkuk.batnam.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LogResponseDto {
    private Long id;
    private String logDate;
    private String captureURL;
    private String result;
    private String resultURL;
    private Long sectorId;
}
