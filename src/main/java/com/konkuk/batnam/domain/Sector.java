package com.konkuk.batnam.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sector_id")
    private Long id;

    @Column(name = "sector_name", columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(name = "sector_cam_url",columnDefinition = "VARCHAR(2083)")
    private String camUrl;

    @Column(name = "sector_sequence",columnDefinition = "INTEGER")
    private String sequence;

    @ManyToOne
    @JoinColumn(name = "air_strip_id")
    private AirStrip airStrip;

    @OneToMany(mappedBy = "sector")
    private List<Log> logList = new ArrayList<>();

}
