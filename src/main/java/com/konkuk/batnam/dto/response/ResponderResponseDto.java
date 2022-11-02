package com.konkuk.batnam.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponderResponseDto  {
    private Long id;
    private String type;
    private String name;
    private String detail;
    private String dest;
}
