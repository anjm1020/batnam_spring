package com.konkuk.batnam.domain;

import lombok.*;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Column(name = "air_strip_name", columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(name = "air_strip_start_zone", columnDefinition = "VARCHAR(10)")
    private String startZone;

    @Column(name = "air_strip_end_zone", columnDefinition = "VARCHAR(10)")
    private String endZone;

    @Builder.Default
    @OneToMany(mappedBy = "airStrip")
    private List<Sector> sectorList = new ArrayList<>();

    public void addSector(Sector sector) {
        sectorList.add(sector);
    }

    public void deleteSector(Sector sector) {
        sectorList.remove(sector);
    }
}
