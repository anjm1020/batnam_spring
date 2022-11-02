package com.konkuk.batnam.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AirStrip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "air_strip_id")
    private Long id;

    @Column(name = "air_strip_code", columnDefinition = "VARCHAR(12)")
    private String code;

    @Column(name = "air_strip_name", columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(name = "air_strip_direction", columnDefinition = "INTEGER")
    private String direction;

    @Column(name = "air_strip_last_date")
    private LocalDate lastDate;

    @OneToMany(mappedBy = "airStrip")
    private List<Sector> sectorList = new ArrayList<>();

}
