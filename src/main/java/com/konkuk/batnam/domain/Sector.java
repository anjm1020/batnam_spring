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
    private String camURL;

    @Column(name = "sector_x",columnDefinition = "VARCHAR(20)")
    private String x;

    @Column(name = "sector_y",columnDefinition = "VARCHAR(20)")
    private String y;

    @ManyToOne
    @JoinColumn(name = "air_strip_id")
    private AirStrip airStrip;

    @OneToMany(mappedBy = "sector")
    private List<Log> logList = new ArrayList<>();

}
