package com.konkuk.batnam.dto.request.responder;

import com.konkuk.batnam.domain.Responder;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponderUpdateDto {
    private Long id;
    private String type;
    private String name;
    private String detail;
    private String dest;

    public void update(Responder entity) {
        entity.setType(this.type);
        entity.setName(this.name);
        entity.setDetail(this.detail);
        entity.setDest(this.dest);
    }
}
