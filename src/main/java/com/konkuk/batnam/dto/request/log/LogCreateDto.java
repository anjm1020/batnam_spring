package com.konkuk.batnam.dto.request.log;

import com.konkuk.batnam.domain.Log;
import com.konkuk.batnam.domain.ObservationObject;
import com.konkuk.batnam.domain.Sector;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LogCreateDto {
    private String captureURL;
    private String result;
    private String resultURL;
    private Long sectorId;
    private Long objectId;

    public Log toEntity(Sector sector, ObservationObject object) {
        return Log.builder()
                .captureURL(this.captureURL)
                .result(this.result)
                .resultURL(this.resultURL)
                .sector(sector)
                .object(object)
                .build();
    }

}
