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

    public Log toEntity(Sector sector) {
        return Log.builder()
                .captureURL(this.captureURL)
                .result(this.result)
                .resultURL(this.resultURL)
                .objectName(this.objectName)
                .sector(sector)
                .build();
    }

}
