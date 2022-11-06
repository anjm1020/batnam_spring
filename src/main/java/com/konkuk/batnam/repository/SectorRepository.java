package com.konkuk.batnam.repository;

import com.konkuk.batnam.domain.AirStrip;
import com.konkuk.batnam.domain.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface SectorRepository extends JpaRepository<Sector, Long> {
    List<Sector> findAllByAirStrip(AirStrip airStrip);
}
