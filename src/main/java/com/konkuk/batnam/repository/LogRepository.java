package com.konkuk.batnam.repository;

import com.konkuk.batnam.domain.Log;
import com.konkuk.batnam.domain.Sector;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {

    List<Log> findAllBySector(Sector sector, Pageable pageable);
    List<Log> findAllBySector(Sector sector);

    int countAllByLogDateBetween(LocalDateTime logDateStart, LocalDateTime logDateEnd);

    List<Log> findAllByLogDateBetween(LocalDateTime logDateStart, LocalDateTime logDateEnd);

    int countAllBySector(Sector sector);
}
