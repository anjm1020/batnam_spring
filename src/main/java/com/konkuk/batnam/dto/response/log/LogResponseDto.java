package com.konkuk.batnam.dto.response.log;

import com.konkuk.batnam.domain.Log;
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

    public static LogResponseDto toResponseDto(Log entity) {
        return new LogResponseDto(
                entity.getId(),
                entity.getLogDate().toString(),
                entity.getCaptureURL(),
                entity.getResult(),
                entity.getResultURL(),
                entity.getSector().getId()
        );
    }
}
