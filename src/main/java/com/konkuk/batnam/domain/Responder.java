package com.konkuk.batnam.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Responder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "responder_id")
    private Long id;

    @Column(name = "responder_type", columnDefinition = "VARCHAR(12)")
    private String type;

    @Column(name = "responder_name", columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(name = "responder_detail", columnDefinition = "VARCHAR(50)")
    private String detail;

    @Column(name = "responder_dest", columnDefinition = "VARCHAR(50)")
    private String dest;

    @OneToOne
    @JoinColumn(name = "air_strip_id")
    private AirStrip airStrip;
}
