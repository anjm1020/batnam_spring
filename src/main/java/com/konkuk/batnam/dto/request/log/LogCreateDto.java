package com.konkuk.batnam.dto.request.log;

import com.konkuk.batnam.domain.Log;
import com.konkuk.batnam.domain.Sector;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LogCreateDto {
    private String objectName;
    private String captureURL;
    private String result;
    private String resultURL;

    private Long sectorId;

    private Boolean isCritical=false;

    public LogCreateDto(String objectName, String captureURL, String result, String resultURL, Long sectorId) {
        this.objectName = objectName;
        this.captureURL = captureURL;
        this.result = result;
        this.resultURL = resultURL;
        this.sectorId = sectorId;
        this.isCritical = false;
    }

    public Log toEntity(Sector sector) {
        return Log.builder()
                .captureURL(this.captureURL)
                .result(this.result)
                .resultURL(this.resultURL)
                .objectName(this.objectName)
                .isCritical(isCritical)
                .sector(sector)
                .build();
    }

}
